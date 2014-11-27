package ttt.player

import ttt.board.Board

trait Player {
  def getMove(board: Board): Option[Int]
  def mark: String
}
