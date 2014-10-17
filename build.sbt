import play.Project._

name := "PreprID"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq("ws.securesocial" %% "securesocial" % "2.1.4", javaCore)

// The Maven2 repo from Sonatype
resolvers += Resolver.sonatypeRepo("releases")

playJavaSettings
