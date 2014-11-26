package ttt.display

import ttt.board.Board

trait Display {
  def showBoard(board: Board)
  def invalidMove()
  def notifyTurn(mark: String)
  def announceResult(mark: Option[Any])
}
