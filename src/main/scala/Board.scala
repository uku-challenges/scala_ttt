package ttt.board

import ttt.mark.Mark._

object Board {
  def empty() = new Board(Vector(0, 1, 2,
                                 3, 4, 5, 
                                 6, 7, 8))

  def make(squares: Any*) = {
    new Board(squares)
  }
}

class Board(squares: Seq[Any]) {
  val winningIndices = Vector(
    Vector(0, 1, 2), Vector(3, 4, 5),
    Vector(6, 7, 8), Vector(0, 3, 6),
    Vector(1, 4, 7), Vector(2, 5, 8),
    Vector(0, 4, 8), Vector(2, 4, 6))

  def markSquare(index: Int, mark: String) = {
    new Board(squares updated(index, mark))
  }

  def squareAt(index: Int) = {
    squares(index)
  }

  def isValidMove(index: Int) = {
    validMoves.contains(index)
  }

  def validMoves = {
    squares.filterNot(isPlayer)
  }

  def isOver = {
    winner.isDefined || isDraw
  }

  def isDraw = {
    isFull && !winner.isDefined
  }

  def winner: Option[Any] = {
    winningCombinations.foreach(combination =>
      if(isWinning(combination))
        return Some(combination.head)
    ) 
    None
  }

  def asSeq = squares

  private def isFull = {
    validMoves.isEmpty
  }

  private def isWinning(combination: Seq[Any]) = {
    combination == Vector(X,X,X) || combination ==  Vector(O,O,O)
  }

  private def winningCombinations: Seq[Seq[Any]] = {
    winningIndices.map(resolveOnSquares)
  }

  private def resolveOnSquares(indices: Seq[Int]): Seq[Any] = {
    indices.map(squares)
  }

  private def isPlayer(sq: Any) = {
    sq == X || sq == O
  }
}
