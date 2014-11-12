package org.preprid.api.rest

import java.net.URL

import akka.actor.Actor
import com.example.rest.RouteExceptionHandlers
import shapeless.{HNil, ::}
import spray.http.Uri.Path
import spray.http.{HttpResponse, HttpRequest}
import spray.httpx.SprayJsonSupport
import spray.routing.PathMatcher.Matching
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


  val apiEndpoint = extract(ctx => {
    new URL(PreprId.HTTP_PROTOCOL(),
            ctx.request.uri.authority.host.address, ctx.request.uri.authority.port.toInt,
            "") //The file component of the URL.. could be "/"
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
    apiEndpoint { apiEndpointUrl =>

      pathPrefix(Segment / Segment / Segment) { (lastName, firstName, disambiguation) =>

        val preprId = new PreprId(apiEndpointUrl, lastName, firstName,disambiguation)

        pathEndOrSingleSlash {
          get {
            complete {
              "Get the resource with id " + preprId.toString()
            }
          } ~
          post {
            complete {
              "Post the resource with id " + preprId.toString()
            }
          } ~
          put {
            complete {
              "Put the resource with id " + preprId.toString()
            }
          } ~
          delete {
            complete {
              "Delete the resource with id " + preprId.toString()
            }
          }
        } ~
        path(RestPath) { view =>
          get {
            complete{
              s"Get the $view with id " + preprId.toString()
            }
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
}
