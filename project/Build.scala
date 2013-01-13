import sbt._
import Keys._

import info.sumito3478.aprikot.sbt._

object AprikotCombinatorBuild extends Build {

  lazy val project = Project(
    id = "aprikot-io",
    base = file(".")
  ).settings(StandardProject.newSettings :_*
  ).settings(
    Seq(
      version := "0.0.1-SNAPSHOT"
    ): _*
  )

}

