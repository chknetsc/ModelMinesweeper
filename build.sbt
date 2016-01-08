name := "Minesweeper_Scala"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "2.4.14" % "test"
)

libraryDependencies += "org.specs2" % "specs2-junit_2.11" % "2.4.14"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11+"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.1"

libraryDependencies += "com.escalatesoft.subcut" %% "subcut" % "2.1"

libraryDependencies += "org.scaldi" %% "scaldi" % "0.5.7"

libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.1"
