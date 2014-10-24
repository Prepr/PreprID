import sbt.Keys._
import sbt._

object Common {
  val settings: Seq[Setting[_]] = Seq(
    organization := "so.paws",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.10.4"
  )
}
