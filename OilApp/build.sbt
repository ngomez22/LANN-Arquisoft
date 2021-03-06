name := """OilApp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, LauncherJarPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  evolutions,
  javaJdbc,
  cache,
  javaWs,
  "org.postgresql" % "postgresql" % "9.4.1209.jre7",
  "be.objectify" %% "deadbolt-java" % "2.5.3",
  "org.mindrot" % "jbcrypt" % "0.3m",
  filters
)
