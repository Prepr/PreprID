package org.preprid.api.rest

import java.net.URL

import akka.actor.Actor
import com.example.rest.RouteExceptionHandlers
import shapeless.HNil
import spray.http.{HttpResponse, HttpRequest}
import spray.http.Uri.Path
import spray.httpx.SprayJsonSupport
import spray.routing.PathMatcher.{Unmatched, Matched}
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

  val preprIdRegex = """^([^(/|#)]+)/([^(/|#)]+)(#.+)?$""".r

  val apiEndpoint = extract(ctx => {
    new URL(PreprId.HTTP_PROTOCOL(),
            ctx.request.uri.authority.host.address, ctx.request.uri.authority.port.toInt,
            "") //The file component of the URL.. could be "/"
  })

  implicit def createLogEntry(text: String): Some[LogEntry] = {
    Some(LogEntry(text, DebugLevel))
  }
  def loggingMagnet(request: HttpRequest): Any => Option[LogEntry] = {
    case response: HttpResponse => "Responded to request " + request.toString + " with status code " + response.status
    case Rejected(rejections) => "Request " + request.toString + " rejected: " + rejections.toString
    case x => "Request " + request.toString + " not handled: " + x.toString
  }

  val preprIdRoute: Route = logRequestResponse(loggingMagnet _) {
    apiEndpoint { apiEndpointUrl =>

      object PreprIdSegments extends PathMatcher1[List[PreprId]] {
        def apply(path: Path) = {
          println(path toString)
          path match {
            case preprIdRegex(lastName, firstName, fragment) => {
              val disambiguation = if (fragment == null) {
                ""
              } else {
                fragment.substring(1)
              }

              Matched(Path.Empty, List(new PreprId(apiEndpointUrl, lastName, firstName, disambiguation)) :: HNil)
            }
            case _ => Unmatched
          }
        }
      }

      path("learner" / PreprIdSegments) { preprIdList =>
        get {
          complete("Get the learner with id " + preprIdList mkString)
        } ~
          post {
            complete {
              "Post the learner with id " + preprIdList mkString
            }
          } ~
          put {
            complete {
              "Put the learner with id " + preprIdList mkString
            }
          } ~
          delete {
            complete {
              "Delete the learner with id " + preprIdList mkString
            }
          }

      }
    }
  }
}
