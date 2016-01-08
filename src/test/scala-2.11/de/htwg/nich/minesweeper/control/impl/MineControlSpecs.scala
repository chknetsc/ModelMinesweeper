package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.model.GameState
import org.specs2.mutable.SpecificationWithJUnit

import scala.swing.Reactor

/**
  * Created by chris on 05.01.2016.
  */
class MineControlSpecs extends SpecificationWithJUnit  {

  "New MineControl with simple Reactor" should {
    val mineControl = new MineControl

    class MySpecsReactor extends Reactor {
      listenTo(mineControl)
      var gameMessage = false
      var updatePosition = false
      var newGame = false

      reactions += {
        case e: UpdatePosition =>
          updatePosition = true
        case e: GameMessage =>
          gameMessage = true
        case e: NewGame =>
          newGame = true
      }
    }
    val myReactor = new MySpecsReactor


    "Start New Game with correct Size and Mines" in {
      mineControl.newGame("Player1", 10, 10, 3) must beTrue
    }

    "Hanlde Input Left Click" in {
      mineControl.handleInput(1, 1, 1)  must beFalse
    }

    "Hanlde Input Right Click" in {
      mineControl.handleInput(3, 3, 3)  must beFalse
    }



    "Handle First Click Input" in {
      mineControl.newGame("Player1", 10, 10, 3)
      mineControl.gameData.currentGameState = GameState.FirstClick
      mineControl.updateMineField
      mineControl.gameData.currentGameState must not be empty
    }

    "Handle InGame Click Input" in {
      mineControl.newGame("Player1", 10, 10, 3)
      mineControl.gameData.currentGameState = GameState.InGame
      mineControl.updateMineField
      mineControl.gameData.currentGameState must not be empty
    }

    "Invalid GameState" in {
      mineControl.gameData.currentGameState = GameState.Won
      mineControl.updateMineField
      myReactor.gameMessage must beTrue
    }

    "Won Game" in {
      mineControl.gameData.currentGameState = GameState.Won
      mineControl.checkGameState
      myReactor.gameMessage must beTrue
    }

    "Lost Game" in {
      mineControl.gameData.currentGameState = GameState.Lost
      mineControl.checkGameState
      myReactor.gameMessage must beTrue
    }

    "Start New Game with not correct Size and Mines" in {
      mineControl.newGame("Player1", 10, 10, 110) must beFalse
    }

    "Start New Game with not correct Size and Mines" in {
      mineControl.newGame("Player1", 10, 10, 80) must beTrue
    }
  }

}
