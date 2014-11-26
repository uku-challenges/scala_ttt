package ttt.console_display

import java.io.Writer
import ttt.board.Board
import ttt.display.Display

object ConsoleDisplay {
}

class ConsoleDisplay(out: Writer) extends Display {

  private val template = 
    """ #* | * | *
        #--+---+--
        #* | * | *
        #--+---+--
        #* | * | *
   #""".stripMargin('#')

  private val placeHolder = "\\*"

  def showBoard(board: Board) {
    print(buildBoard(board))
  }

  def invalidMove() = {
    print("Invalid Move, try again\n")
  }

  def notifyTurn(mark: String) = {
    print("\n" + mark + " turn\n")
  }

  def announceResult(winnerMark: Option[Any]) = {
    winnerMark match {
      case winnerMark: Some[String] => print(winnerMark.get + " has won!\n")
      case _                        => print("It's a draw!\n")
    }
  }

  private def print(msg: String) {
    out.write(msg)
    out.flush
  }

  private def buildBoard(board: Board): String = {
     var boardOutput = new String(template) 
     board.asSeq.foreach(cell =>
         boardOutput = boardOutput.replaceFirst(placeHolder, formatCell(cell))
     )
     boardOutput
  }

  private def formatCell(cell: Any): String = {
    cell match {
      case cell: String => cell
      case cell: Int => (cell + 1).toString
    }
  }
}
