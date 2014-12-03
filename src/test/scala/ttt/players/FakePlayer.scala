package ttt.players

import ttt.Mark
import ttt.Board

object FakePlayer {
  def X(moves: Int*) = {
    new FakePlayer(moves.toList, Mark.X)
  }

  def O(moves: Int*) = {
    new FakePlayer(moves.toList, Mark.O)
  }
}

class FakePlayer(var moves: Seq[Int], private val _mark:String) extends Player {
  def getMove(board: Board) = {
    val move = moves.headOption
    moves = moves.drop(1)
    move
  }

  def mark: String = _mark
}

