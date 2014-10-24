package com.example.rest

import com.example.exceptions.ProfileNotFoundException

import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing.Directive._
import spray.routing.{ExceptionHandler, HttpService}
import spray.util.LoggingContext

/**
 * Defines a custom ExceptionHandler for our application 
 */
trait RouteExceptionHandlers extends HttpService {
  
  implicit def exceptionHandler(implicit log: LoggingContext) = ExceptionHandler {
    case e: ProfileNotFoundException => complete(StatusCodes.NotFound, e.getMessage())        
  }    

}
