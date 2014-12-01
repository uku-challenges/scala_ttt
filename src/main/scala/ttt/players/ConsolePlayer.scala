package ttt.players

import ttt.Board
import ttt.display.Display

class ConsolePlayer(private val _mark: String, display: Display) extends Player{

  def getMove(board: Board): Option[Int] = {
    display.getMove()
  }

  def mark = _mark
}
