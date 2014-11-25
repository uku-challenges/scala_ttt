package ttt.console_player

import ttt.player.Player
import java.io.BufferedReader

class ConsolePlayer(private val _mark: String, in: BufferedReader) extends Player{

  def getMove(): Option[Int] = {
    tryConvert(in.readLine)
  }

  def mark = _mark

  private def tryConvert(input: String) = {
    try {
      Some(input.toInt - 1)
    } catch {
      case e:NumberFormatException => None
    }
  }
}
