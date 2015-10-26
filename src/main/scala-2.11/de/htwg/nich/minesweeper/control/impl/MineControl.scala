package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.control.impl.MineFieldRefresher.ClickMode
import de.htwg.nich.minesweeper.model.{GameState, GameData}


/**
 * Created by Boldi on 19.10.2015.
 */
class MineControl {

  val gameData = new GameData

  def handleClick(clickMode:ClickMode.Value): Unit ={
    if(gameData.currentGameState == GameState.NewGame){

    }
  }


}
