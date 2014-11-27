package ttt.console_player

import ttt.player.Player
import ttt.board.Board
import ttt.display.Display
import java.io.BufferedReader

class ConsolePlayer(private val _mark: String, display: Display) extends Player{

  def getMove(board: Board): Option[Int] = {
    display.getMove()
  }

  def mark = _mark
}
