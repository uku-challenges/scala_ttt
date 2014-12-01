package ttt

import ttt.display.Display
import ttt.players.Player

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
    display.showBoard(board)
    if(isOver) {
      display.announceResult(winnerMark)
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
