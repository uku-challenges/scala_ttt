package ttt.console_display

import java.io.Writer
import ttt.board.Board

object ConsoleDisplay {
}

class ConsoleDisplay(out: Writer) {

  val template = 
    """ #* | * | *\n
        #--+---+--\n
        #* | * | *\n
        #--+---+--\n
        #* | * | *\n
   #""".stripMargin('#')

  val placeHolder = "\\*"

  def showBoard(board: Board) {
    out.write(buildBoard(board))
    out.flush
  }

  private def buildBoard(board: Board): String = {
     var boardOutput = new String(template) 
     board.asSeq.foreach(cell =>
         boardOutput = boardOutput.replaceFirst(placeHolder, cell.toString)
     )
     boardOutput
  }
}
