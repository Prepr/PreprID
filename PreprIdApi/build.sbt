name := """spray-rest-memcached"""

version := "1.0"

scalaVersion := "2.10.3"

// Change this to another test framework if you prefer
libraryDependencies ++= {
  val akkaV = "2.2.4"
  val sprayV = "1.2.0"
  Seq(
    "io.spray"            %   "spray-can"     % sprayV,
    "io.spray"            %   "spray-routing" % sprayV,
    "io.spray"            %   "spray-caching" % sprayV,
    "io.spray"            %   "spray-testkit" % sprayV  % "test",
    "com.h2database"      %   "h2"            % "1.2.127",
    "io.spray"            %%  "spray-json"    % "1.2.6",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.slf4j"           %   "slf4j-log4j12" % "1.7.7",
    "org.clapper"         %%  "grizzled-slf4j"% "1.0.2",
    "org.specs2"          %%  "specs2"        % "2.2.3" % "test",
    "com.typesafe.slick"  %%  "slick"         % "2.0.2",
    "c3p0"                %   "c3p0"          % "0.9.0.4",
    "net.spy"             %   "spymemcached"  % "2.11.4",
    "org.apache.logging.log4j"       %  "log4j"           % "2.0-rc2",
    "com.thimbleware.jmemcached"  % "jmemcached-core"  %  "1.0.0",
    "com.hazelcast"       %   "hazelcast"     % "3.1.7"
  )
}


val starth2 = taskKey[Unit]("")

starth2 := {
  val logger = streams.value.log
  Process("java -jar h2/bin/h2.jar") ! logger match {
    case 0 => println("H2 Database has been started")
    case n => sys.error(s"Could not restart the project, exit code: $n")
  }
}


