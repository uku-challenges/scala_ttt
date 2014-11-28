package ttt.console_display

import java.io.{Writer, BufferedReader}
import ttt.mark.Mark.{X,O}
import ttt.board.Board
import ttt.display.Display

object ConsoleDisplay {
}

class ConsoleDisplay(in: BufferedReader, out: Writer) extends Display {
  private val ANSI_CLS     = "\u001b[H\u001b[2J"
  private val ANSI_GREEN   = "\u001b[2;32m"
  private val ANSI_YELLOW  = "\u001b[2;33m"
  private val ANSI_RESET   = "\u001b[0m"

  private val colorFor = Map(
    X -> ANSI_GREEN,
    O -> ANSI_YELLOW
  )

  private val template = 
    """ #* | * | *
        #--+---+--
        #* | * | *
        #--+---+--
        #* | * | *
   #""".stripMargin('#')

  private val placeHolder = "\\*"
  
  def select(subject: String, options: Seq[String]): Option[String] = {
    clear()
    print("Select " + subject + ":\n")
    selectIndex(options) match {
      case Some(index) => options.lift(index)
      case _           => None
    }
  }

  def playAgain(): Boolean = {
    print("Enter 'y' to play again :")
    in.readLine == "y"
  }

  def getMove(): Option[Int] = {
    print("Enter a move: ")
    tryZeroIndexedInt(in.readLine)
  }

  def showBoard(board: Board) {
    clear()
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

  private def print(msg: String) = {
    out.write(msg)
    out.flush
  }

  private def selectIndex(options: Seq[String]) = {
    options.zipWithIndex.foreach(printOption)
    tryZeroIndexedInt(in.readLine)
  }

  private def printOption(option: (String, Int)) = {
    print(option._2 + 1 + ". " + option._1 + "\n")
  }

  private def tryZeroIndexedInt(input: String) = {
    try {
      Some(input.toInt - 1)
    } catch {
      case e:NumberFormatException => None
    }
  }

  private def buildBoard(board: Board): String = {
     var boardString = new String(template) 
     board.asSeq.foreach(cell =>
         boardString = boardString.replaceFirst(placeHolder, formatCell(cell))
     )
     boardString
  }

  private def formatCell(cell: Any): String = {
    cell match {
      case cell: Int => (cell + 1).toString
      case cell: String => coloredCell(cell)
    }
  }

  private def coloredCell(cell: String) = {
    colored(cell, colorFor(cell))
  }

  private def clear() = {
    print(ANSI_CLS)
  }

  private def colored(str: String, color: String) = {
    color + str + ANSI_RESET
  }
}
