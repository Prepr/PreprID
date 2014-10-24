package com.example.dto

import spray.json.DefaultJsonProtocol

case class Profile(id:Option[Int], firstName:String, lastName:String, email:String)

object ProfileJsonProtocol extends DefaultJsonProtocol {
  implicit val profileFormat = jsonFormat4(Profile)
}
