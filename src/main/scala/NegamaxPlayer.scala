package ttt.negamax_player

import ttt.board.Board
import ttt.mark.Mark

class NegamaxPlayer(private val _mark: String){
  def mark = _mark

  def getMove(board: Board): Option[Int] = {
    var bestMove: Option[Int] = None
    var bestScore = -100

    board.validMoves.foreach{ move =>
      val newBoard = board.markSquare(move, mark)
      val score = -negamax(newBoard, Mark.opponentOf(mark))
      if(score > bestScore) {
        bestScore = score
        bestMove  = Some(move)
      }
    }
    bestMove
  }

  def negamax(board: Board, player: String): Int = {
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
