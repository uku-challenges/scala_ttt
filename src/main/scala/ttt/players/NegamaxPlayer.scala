package ttt.players

import ttt.{Board, Mark}

import scala.collection.mutable.HashMap

class NegamaxPlayer(private val _mark: String) extends Player{

  private val MIN =  -100
  private val MAX =  100

  def mark = _mark

  def getMove(board: Board): Option[Int] = {
    val scores    = scoreMoves(board)
    val bestScore = scores.keys.max
    Some(scores(bestScore))
  }

  private def scoreMoves(board: Board) = {
    val scoresWithMoves = new HashMap[Int, Int]

    board.validMoves.map{ move =>
      val score = -negamax(board.markSquare(move, mark), Mark.opponentOf(mark), MIN, MAX)
      scoresWithMoves(score) = move
    }
    scoresWithMoves
  }

  private def negamax(board: Board, player: String, alpha: Int, beta: Int): Int = {
    if(board.isOver) {
      return score(board, player)
    }

    var mutAlpha = alpha
    var bestScore = MIN
    board.validMoves.foreach{ move =>
      val newBoard = board.markSquare(move, player)
      val score = -negamax(newBoard, Mark.opponentOf(player), -beta, -mutAlpha)
      bestScore = math.max(score, bestScore)
      mutAlpha  = math.max(score, mutAlpha)
      if(mutAlpha >= beta) {
        return bestScore
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
