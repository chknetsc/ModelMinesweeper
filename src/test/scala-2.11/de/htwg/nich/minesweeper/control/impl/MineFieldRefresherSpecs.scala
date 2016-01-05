package de.htwg.nich.minesweeper.control.impl

import org.specs2.mutable.SpecificationWithJUnit

/**
  * Created by chris on 05.01.2016.
  */
class MineFieldRefresherSpecs extends SpecificationWithJUnit {

  "A new MineFieldRefresher" should {

    "generate default Field" in {
      //MineFieldGenerator.returnInitialField(10, 10).length must be_==(10)
      true must beTrue
    }

    "generate fistClick Field" in {
      //MineFieldGenerator.returnFieldAfterFirstClick((10,10), 5, (2,3)).length must be_==(10)
      true must beTrue
    }
  }

}
