package de.htwg.nich.minesweeper.model

import org.specs2.mutable.SpecificationWithJUnit

/**
  * Created by chris on 05.01.2016.
  */
class GameDataSpec extends SpecificationWithJUnit {

  "GameData for a default Player" should {
    val gameData = new GameData

    "Playername must be Player1" in {
      gameData.playerName must beMatching("Player1")
    }
  }

  "GameState for newGame" should {
    var currentState = GameState.NewGame

    "Change GameState" in {
      currentState = GameState.InGame
      currentState must be_==(GameState.InGame)
    }
  }

}
