package ttt.game

import ttt.board.Board
import ttt.mark.Mark.{X, O}
import ttt.player.Player
import ttt.display.Display
import ttt.console_display.ConsoleDisplay
import ttt.console_player.ConsolePlayer
import ttt.negamax_player.NegamaxPlayer

import java.io.{PrintWriter, BufferedReader, InputStreamReader}

class Game(var board: Board, private val display: Display, private val players: Seq[Player]) {
  private var playerStream = Stream.continually(players.toStream).flatten

  def play() {
    while(!isOver) {
      playTurn
    }
    display.showBoard(board)
    display.announceResult(winnerMark)
  }

  def playTurn() = {
    display.notifyTurn(currentPlayer.mark)
    display.showBoard(board)
    val moveOption = currentPlayer.getMove(board)
    if(canMakeMove(moveOption)) {
      makeMove(moveOption.get)
      nextPlayerTurn()
    } else {
      display.invalidMove()
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
