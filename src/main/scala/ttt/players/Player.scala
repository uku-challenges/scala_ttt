package ttt.players

import ttt.Board

trait Player {
  def getMove(board: Board): Option[Int]
  def mark: String
}
