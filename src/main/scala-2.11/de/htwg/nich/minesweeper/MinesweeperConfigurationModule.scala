package de.htwg.nich.minesweeper

import de.htwg.nich.minesweeper.model.GameData
import scaldi.{Injectable, Module, DynamicModule}

/**
  * Created by chris on 07.01.2016.
  */
object MinesweeperConfigurationModule extends Injectable {
  implicit val appModule =
    new Module {
      //bind [data] to new GameData
    }
}
