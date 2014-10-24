package com.example.rest

import akka.actor.Actor

import com.example.cache.MemcachedCache
import com.example.config.AppConfig._
import com.example.dao.ProfileDAO
import com.example.dto.Profile
import com.example.dto.ProfileJsonProtocol._

import scala.collection.JavaConversions._
import spray.http.StatusCodes
import spray.http.Uri
import spray.http.HttpMethod
import spray.http.HttpMethods._
import spray.http.HttpRequest
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService
import spray.routing.RequestContext
import spray.routing.directives.CacheKeyer
import spray.routing.directives.CachingDirectives._

class ProfileActor extends Actor with ProfileService {
  def actorRefFactory = context
  def receive = runRoute(profileRoute)
}

/**
 * Provides the RESTful API for managing `Profile`s 
 */
trait ProfileService extends HttpService with SprayJsonSupport with RouteExceptionHandlers  {

  private val profileDAO = ProfileDAO
  
  //The Cache used for storing our RouteResponse
  private val theCache = MemcachedCache.routeCache(Memcached.hosts, Memcached.timeToLive)
  
  //We define a custom keyer that returns just the path and parameters of the request
  //For example, http://localhost:8080/profiles/1 will return /profiles/1
  implicit val relativeUriKeyer = CacheKeyer { 
  	case RequestContext(HttpRequest(GET, uri, _, _, _), _, _) => uri.toRelative
  }  
    
  //Helper method for evicting cache entries used for PUT, POST, and DELETE requests
  private def evictCache(uri: Uri, evictParentPath: Boolean) = {
    theCache.remove(uri.toRelative)
    if(evictParentPath) {
	  val uriString = uri.toRelative.toString
	  theCache.remove(uriString.substring(0, uriString.lastIndexOf("/")))        
    }
  }    
    
  val profileRoute = {
    alwaysCache(theCache) {
      (get & path("profiles")) { //Get all Profiles
        complete {
          profileDAO.getProfiles match { 
            case head :: tail => head :: tail
            case Nil => StatusCodes.NoContent
          }
        }
      } ~
      (post & path("profiles")) { // Create a single Profile
        entity(as[Profile]) { profile => ctx =>
          val insertedProfile = profileDAO.insertProfile(profile)
          evictCache(ctx.request.uri, false)              
          ctx.complete(StatusCodes.Created, insertedProfile)
        }
      } ~
      (get & path("profiles" / IntNumber)) { id => //Get a single Profile
        val profile = profileDAO.getProfile(id)
        complete(profile)
      } ~
      (delete & path("profiles" / IntNumber)) { id => ctx => //Delete a single Profile            
        profileDAO.deleteProfile(id)
        evictCache(ctx.request.uri, true)
        ctx.complete(StatusCodes.NoContent)
      } ~
	  (put & path("profiles" / IntNumber)) { id => //Update a single Profile
	    entity(as[Profile]) { profile => ctx =>
	      profileDAO.updateProfile(id, profile)
	      evictCache(ctx.request.uri, true)
	      ctx.complete(profile)
	    }
	  }
    }
  }
    
}
