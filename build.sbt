ThisBuild / scalaVersion := "2.13.11"
run / fork := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.20",
  "com.typesafe.akka" %% "akka-stream" % "2.6.20",
  "com.typesafe.akka" %% "akka-http" % "10.2.10",
  "ch.qos.logback" % "logback-classic" % "1.4.11",
  "org.scalactic" %% "scalactic" % "3.2.16",
  "org.scalatest" %% "scalatest" % "3.2.16" % Test
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

Docker / packageName := "sample-webapp"
Docker / version := "2.0.0"

dockerBaseImage := "openjdk:latest"
dockerExposedPorts := List(8080)

assembly / mainClass := Some("Main")
ThisBuild / assemblyMergeStrategy := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}
