package ttt.console_display

import java.io.{Writer, BufferedReader}
import ttt.board.Board
import ttt.display.Display

object ConsoleDisplay {
}

class ConsoleDisplay(in: BufferedReader, out: Writer) extends Display {

  private val template = 
    """ #* | * | *
        #--+---+--
        #* | * | *
        #--+---+--
        #* | * | *
   #""".stripMargin('#')

  private val placeHolder = "\\*"
  
  def select(subject: String, options: Seq[String]): Option[String] = {
    print("Select " + subject + ":\n")
    selectIndex(options) match {
      case Some(index) => options.lift(index)
      case _           => None
    }
  }

  def getMove(): Option[Int] = {
    print("Enter a move: ")
    tryZeroIndexedInt(in.readLine)
  }

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
      case winnerMark: Some[Any]    => print(winnerMark.get + " has won!\n")
      case _                        => print("It's a draw!\n")
    }
  }

  private def print(msg: String) {
    out.write(msg)
    out.flush
  }

  private def selectIndex(options: Seq[String]) = {
    options.zipWithIndex.foreach(printOption)
    tryZeroIndexedInt(in.readLine)
  }

  private def printOption(optionWithIndex: (String, Int)) = {
    print(optionWithIndex._2 + 1 + ". " + optionWithIndex._1 + "\n")
  }

  private def tryZeroIndexedInt(input: String) = {
    try {
      Some(input.toInt - 1)
    } catch {
      case e:NumberFormatException => None
    }
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
