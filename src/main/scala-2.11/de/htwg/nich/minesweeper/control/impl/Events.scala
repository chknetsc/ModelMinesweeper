package de.htwg.nich.minesweeper.control.impl

import scala.swing.event.Event

/**
 * Created by Chris on 30.11.2015.
 */
case class UpdatePosition() extends Event

case class GameMessage(message: String) extends Event

case class NewGame() extends Event
