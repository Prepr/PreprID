package org.preprid.api.rest

import akka.actor.Actor
import com.example.rest.RouteExceptionHandlers
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService
import spray.routing.Route

/**
 * Created by abouelna on 02/11/2014.
 */
class PreprIdActor extends Actor with PreprIdService {
  def actorRefFactory = context
  def receive = runRoute(preprIdRoute)
}

trait PreprIdService extends HttpService with SprayJsonSupport with RouteExceptionHandlers  {

  val preprIdRoute: Route = {
    path("learner" / Str) {
      get {

      } ~
      post {

      } ~
      put {

      } ~
      delete {

      }

    }
  }
}
