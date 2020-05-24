name := "scala-utils"

organization := "mrks"

version := "0.1.1"

scalaVersion := "2.13.1"

crossScalaVersions := Seq("2.12.10", "2.13.1")

licenses += ("Apache-2.0", url("https://github.com/markussammallahti/scala-utils/blob/master/LICENSE"))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.+" % Provided,
  "com.typesafe.akka" %% "akka-stream" % "2.5.+" % Provided
)
