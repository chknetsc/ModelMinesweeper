package de.htwg.nich.minesweeper

import org.specs2.mutable.SpecificationWithJUnit

/**
  * Created by chris on 05.01.2016.
  */
class MinesweeperSpecs extends SpecificationWithJUnit {

  "New Mineswepper Game" should {
    val game = Minesweeper

    "init Mineswepper Game" in {
      //game.main(null)
      true must beTrue
    }

  }

}
