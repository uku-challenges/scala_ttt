package ttt.game

import ttt.board.Board
import ttt.player.Player

class Game(var board: Board, players: Seq[Player]) {
  private var playerStream = Stream.continually(players.toStream).flatten

  def play() {
    while(!isOver) {
      playTurn
    }
  }

  def playTurn() = {
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
