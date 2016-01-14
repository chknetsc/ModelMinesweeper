package de.htwg.nich.minesweeper.control.impl

import akka.actor.Actor
import de.htwg.nich.minesweeper.control.impl.GameDataMessage.{UpdateField, InitEmptyField, InitField, ReturnGameField}
import de.htwg.nich.minesweeper.control.impl.MineFieldGenerator._

/**
  * Created by Boldi on 21.12.2015.
  */
class MineControlAkka extends Actor {

  val mineControl:MineControl = new MineControl


  override def receive: Receive = {
     case UpdateField(gameData) =>
      sender ! mineControl.updateGame()
  }
}
