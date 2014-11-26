package ttt.game

import ttt.board.Board
import ttt.player.Player
import ttt.display.Display
import ttt.console_display.ConsoleDisplay
import ttt.console_player.ConsolePlayer

import java.io.{PrintWriter, BufferedReader, InputStreamReader}

object Game {
  def main(args: Array[String]) {
    val display = new ConsoleDisplay(new PrintWriter(System.out))
    val xPlayer = new ConsolePlayer("X", new BufferedReader(new InputStreamReader(System.in)))
    val oPlayer = new ConsolePlayer("O", new BufferedReader(new InputStreamReader(System.in)))

    new Game(Board.empty, display, Vector(xPlayer, oPlayer)).play()
  }
}

class Game(var board: Board, private val display: Display, private val players: Seq[Player]) {
  private var playerStream = Stream.continually(players.toStream).flatten

  def play() {
    while(!isOver) {
      playTurn
    }
  }

  def playTurn() = {
    display.showBoard(board)
    val moveOption = currentPlayer.getMove()
    if(canMakeMove(moveOption)) {
      makeMove(moveOption.get)
      nextPlayerTurn()
    }
  }

  def winnerMark = {
    board.winner
  }

  def currentPlayer = {
    playerStream.head
  }

  private def isOver = {
    board.isOver
  }

  private def canMakeMove(moveOption: Option[Int]) = {
    moveOption.isDefined && board.isValidMove(moveOption.get)
  }

  private def makeMove(move: Int) {
    board = board.markSquare(move, currentPlayer.mark)
  }

  private def nextPlayerTurn() {
    playerStream = playerStream.tail
  }
}
