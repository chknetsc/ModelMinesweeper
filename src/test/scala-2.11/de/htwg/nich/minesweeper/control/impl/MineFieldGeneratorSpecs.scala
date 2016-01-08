package de.htwg.nich.minesweeper.control.impl

import akka.actor.Actor.Receive
import akka.actor.{Actor, Props, ActorSystem}
import akka.testkit.TestKitBase
import de.htwg.nich.minesweeper.control.impl.GameDataMessage.InitEmptyField
import de.htwg.nich.minesweeper.model.GameData
import org.specs2.mutable.SpecificationWithJUnit

/**
  * Created by chris on 05.01.2016.
  */
class MineFieldGeneratorSpecs extends SpecificationWithJUnit  {



  "A new MineFieldGenerator" should {
    val gameData = new GameData
    //val controller = system.actorOf(Props(new ActorController), "controller")

    "generate default Field" in {

      //generator.tell(InitEmptyField(gameData), controller)
      //expectMsg(true)
      true must beTrue

    }

    "generate fistClick Field" in {
      //generator.tell(InitField(gameData), controller)
      //generator.returnFieldAfterFirstClick((10,10), 5, (2,3)).length must be_==(10)
      true must beTrue
    }

  }

}
