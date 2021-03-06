val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.4"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

packageName in Docker := "sample-webapp"
version in Docker := "2.0.0"

dockerBaseImage := "openjdk:latest"
dockerExposedPorts := List(8080)
