name := "tictactoe"

version := "0.1"

scalaVersion := "2.11.2"

mainClass in (Compile, run) := Some("ttt.multiple_game_runner.GameRunner")

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.1.3" % "test"

libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
