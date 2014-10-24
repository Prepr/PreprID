name := "db"

Common.settings

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.180",
  "org.sorm-framework" % "sorm" % "0.3.15",
  "com.typesafe.play" %% "play" % "2.3.1",
  "com.typesafe.slick" %% "slick" % "2.0.2"
)
