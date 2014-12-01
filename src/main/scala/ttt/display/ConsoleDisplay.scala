package ttt.display

import java.io.{BufferedReader, Writer}

import ttt.Board
import ttt.Mark.{O, X}

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

  def announceResult(winnerMark: Option[Any]) = {
    winnerMark match {
      case winnerMark: Some[Any]    => print(winnerMark.get + " has won!\n")
      case _                        => print("It's a draw!\n")
    }
  }

  private def print(msg: String) = {
    out.write(msg)
    out.flush()
  }

  private def selectIndex(options: Seq[String]) = {
    options.zipWithIndex.foreach(printOption)
    tryZeroIndexedInt(in.readLine)
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

  private def printOption(option: (String, Int)) = {
    print(option._2 + 1 + ". " + option._1 + "\n")
  }

  private def coloredCell(cell: String) = {
    cell match {
      case X => Ansi.green(X)
      case O => Ansi.yellow(O)
    }
  }

  private def clear() = {
    print(Ansi.clear)
  }
}
