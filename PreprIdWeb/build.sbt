name := """PreprIdWeb"""

Common.settings

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "angularjs" % "1.2.19",
  "org.webjars" % "bootstrap" % "3.2.0" exclude("org.webjars", "jquery"),
  "org.webjars" % "requirejs" % "2.1.14-1" exclude("org.webjars", "jquery")
)

pipelineStages := Seq(rjs, digest, gzip)

lazy val db = project in file("modules/db")

lazy val web = (project in file("modules/web"))
  .enablePlugins(PlayScala)
  .dependsOn(db).aggregate(db)

lazy val users = (project in file("modules/users"))
  .enablePlugins(PlayScala)
  .dependsOn(web, db).aggregate(web, db)

lazy val paws = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(web, db, users)
  .aggregate(web, db, users)

scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code",
  "-language:reflectiveCalls"
)