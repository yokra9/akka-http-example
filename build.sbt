ThisBuild / scalaVersion := "3.3.6"
run / fork := true

libraryDependencies ++= Seq(
  "org.apache.pekko" %% "pekko-actor-typed" % "1.2.1",
  "org.apache.pekko" %% "pekko-stream" % "1.2.1",
  "org.apache.pekko" %% "pekko-http" % "1.2.0",
  "ch.qos.logback" % "logback-classic" % "1.5.19",
  "org.scalactic" %% "scalactic" % "3.2.19",
  "org.scalatest" %% "scalatest" % "3.2.19" % Test
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

Docker / packageName := "sample-webapp"
Docker / version := "2.0.0"

dockerBaseImage := "eclipse-temurin:latest"
dockerExposedPorts := List(8080)

assembly / mainClass := Some("Main")
ThisBuild / assemblyMergeStrategy := {
  case PathList(ps @ _*) if ps.last endsWith "module-info.class" =>
    MergeStrategy.discard
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}
