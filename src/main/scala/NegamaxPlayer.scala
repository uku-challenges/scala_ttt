package ttt.negamax_player

import ttt.board.Board
import ttt.mark.Mark
import scala.collection.mutable.HashMap

class NegamaxPlayer(private val _mark: String){
  def mark = _mark

  def getMove(board: Board): Option[Int] = {
    val scores    = scoreMoves(board)
    val bestScore = scores.keys.max
    Some(scores(bestScore))
  }

  private def scoreMoves(board: Board) = {
    val scoresWithMoves = new HashMap[Int, Int]

    board.validMoves.map{ move =>
      val score = -negamax(board.markSquare(move, mark), Mark.opponentOf(mark))
      scoresWithMoves(score) = move
    }
    scoresWithMoves
  }

  private def negamax(board: Board, player: String): Int = {
    if(board.isOver) {
      return score(board, player)
    }

    var bestScore = -1
    board.validMoves.foreach{ move =>
      val newBoard = board.markSquare(move, player)
      val score = -negamax(newBoard, Mark.opponentOf(player))
      if(score > bestScore) {
        bestScore = score
      }
    }
    bestScore
  }

  private def score(board: Board, player: String) = {
   board.winner match {
     case None               => 0
     case Some(winnerMark)   => if(winnerMark == player) 1 else -1 
   }
  }
}
