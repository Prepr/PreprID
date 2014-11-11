package org.preprid.api.rest

import java.net.URL

import akka.actor.Actor
import com.example.rest.RouteExceptionHandlers
import spray.http.{HttpResponse, HttpRequest}
import spray.httpx.SprayJsonSupport
import spray.routing._

import org.preprid.model.identification.PreprId
import spray.routing.directives.LogEntry
import akka.event.Logging._

/**
 * Created by abouelna on 02/11/2014.
 */
class PreprIdActor extends Actor with PreprIdService {
  def actorRefFactory = context
  def receive = runRoute(preprIdRoute)
}

trait PreprIdService extends HttpService with SprayJsonSupport with RouteExceptionHandlers  {


  val apiEndpointAndFragment = extract(ctx => {
    (new URL(PreprId.HTTP_PROTOCOL(),
            ctx.request.uri.authority.host.address, ctx.request.uri.authority.port.toInt,
            ""), //The file component of the URL.. could be "/"
      ctx.request.uri.fragment)
  })

  implicit def createLogEntry(textConstructor: () => String): Some[LogEntry] = {
    Some(LogEntry(textConstructor(), DebugLevel))
  }
  def loggingMagnet(request: HttpRequest): Any => Option[LogEntry] = {
    case response: HttpResponse => () => "Responded to request " + request.toString + " with status code " + response.status
    case Rejected(rejections) => () => "Request " + request.toString + " rejected: " + rejections.toString
    case x => () => "Request " + request.toString + " not handled: " + x.toString
  }

  val preprIdRoute: Route = logRequestResponse(loggingMagnet _) {
    apiEndpointAndFragment { nonPathUriInfo =>

      val apiEndpointUrl = nonPathUriInfo._1
      val disambiguation = nonPathUriInfo._2 match {
        case Some(fragment) => fragment
        case None => ""
      }

      path("learner" / Segment / Segment) { (lastName, firstName) =>

        val preprId = new PreprId(apiEndpointUrl, lastName, firstName, disambiguation)
        get {
          complete("Get the learner with id " + preprId.toString())
        } ~
          post {
            complete {
              "Post the learner with id " + preprId.toString()
            }
          } ~
          put {
            complete {
              "Put the learner with id " + preprId.toString()
            }
          } ~
          delete {
            complete {
              "Delete the learner with id " + preprId.toString()
            }
          }

      }
    }
  }
}
