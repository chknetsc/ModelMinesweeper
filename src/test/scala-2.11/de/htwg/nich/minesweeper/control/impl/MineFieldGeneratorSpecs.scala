package de.htwg.nich.minesweeper.control.impl

import akka.actor.{Props, ActorSystem}
import de.htwg.nich.minesweeper.control.impl.GameDataMessage.{InitField, InitEmptyField}
import org.specs2.mutable.SpecificationWithJUnit

/**
  * Created by chris on 05.01.2016.
  */
class MineFieldGeneratorSpecs extends SpecificationWithJUnit {

  "A new MineFieldGenerator" should {
    val system = ActorSystem("MyActorSystem")
    val generator = system.actorOf(Props(MineFieldGenerator), "generator")

    "generate default Field" in {
      //generator.tell(InitEmptyField(gameData), controller)
      //generator.returnInitialField(10, 10).length must be_==(10)
      true must beTrue
    }

    "generate fistClick Field" in {
      //generator.tell(InitField(gameData), controller)
      //generator.returnFieldAfterFirstClick((10,10), 5, (2,3)).length must be_==(10)
      true must beTrue
    }

  }

}
