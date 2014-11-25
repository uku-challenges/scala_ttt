package ttt.console_display

import ttt.mark.Mark._
import ttt.board.Board
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import java.io.{Writer, StringWriter}

class ConsoleDisplaySpec extends FunSpec with ShouldMatchers with BeforeAndAfter {

  var out: Writer             = _
  var display: ConsoleDisplay = _

  before {
    out     = new StringWriter()
    display = new ConsoleDisplay(out)
  }

  def occurrences(str:String, substr:String ) = substr.r.findAllMatchIn(str).length

  describe("Console Display") {
    describe("showBoard") {
      it("Does not write a mark if board is emtpy") {
        display.showBoard(Board.empty)

        occurrences(out.toString, "X") should equal(0)
        occurrences(out.toString, "O") should equal(0)
      }

      it("Writes a mark if board has a mark in it") {
        display.showBoard(Board.make(0, 1, 2,
                                     3, X, 5,
                                     6, 7, 8))

        occurrences(out.toString, "X") should equal(1)
      }

      it("Writes as many marks as the board has") {
        display.showBoard(Board.make(O, 1, X,
                                     3, X, O,
                                     X, 7, 8))

        occurrences(out.toString, "X") should equal(3)
        occurrences(out.toString, "O") should equal(2)
      }

      it("Shows the board in correct format") {
        display.showBoard(Board.make(0, 1, 2,
                                     3, X, 5,
                                     6, 7, 8))

        val expected = 
          """ #0 | 1 | 2\n
              #--+---+--\n
              #3 | X | 5\n
              #--+---+--\n
              #6 | 7 | 8\n
          #""".stripMargin('#')

        out.toString should equal(expected)
      }
    }
  }
}