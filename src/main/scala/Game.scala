package ttt.game

import ttt.board.Board
import ttt.player.Player
import ttt.display.Display

class Game(var board: Board, private val display: Display, private val players: Seq[Player]) {
  private var playerStream = Stream.continually(players.toStream).flatten

  def play() {
    updateDisplay()
    while(!isOver) {
      playTurn()
    }
  }

  def playTurn() = {
    val moveOption = currentPlayer.getMove(board)
    makeMoveIfValid(moveOption)
    updateDisplay()
  }

  def winnerMark = {
    board.winner
  }

  def currentPlayer = {
    playerStream.head
  }

  private def makeMoveIfValid(moveOption: Option[Int]) = {
    if(isValid(moveOption)) {
      makeMove(moveOption.get)
      nextPlayerTurn()
    } else {
      display.invalidMove()
    }
  }

  private def updateDisplay() = {
    if(isOver) {
      display.showBoard(board)
      display.announceResult(winnerMark)
    } else {
      display.notifyTurn(currentPlayer.mark)
      display.showBoard(board)
    }
  }

  private def isOver = {
    board.isOver
  }

  private def isValid(moveOption: Option[Int]) = {
    moveOption.isDefined && board.isValidMove(moveOption.get)
  }

  private def makeMove(move: Int) {
    board = board.markSquare(move, currentPlayer.mark)
  }

  private def nextPlayerTurn() {
    playerStream = playerStream.tail
  }
}
