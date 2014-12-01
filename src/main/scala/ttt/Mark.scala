package ttt

object Mark {
  val X = "X"
  val O = "O"

  def isMark(cell: Any) = {
    cell == X || cell == O
  }

  def opponentOf(mark: String) = {
    mark match {
      case X => O
      case O => X
    }
  }
}
