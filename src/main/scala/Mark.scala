package ttt.mark

object Mark {
  val X = "X"
  val O = "O"

  def isMark(cell: Any) = {
    cell == X || cell == O
  }
}
