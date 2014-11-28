package ttt.display

import ttt.board.Board

trait Display {
  def showBoard(board: Board)
  def invalidMove()
  def notifyTurn(mark: String)
  def announceResult(mark: Option[Any])
  def select(message: String , options: Seq[String]): Option[String]
  def getMove(): Option[Int]
  def playAgain(): Boolean
}
