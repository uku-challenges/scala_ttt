package ttt.console_display

import ttt.mark.Mark._
import ttt.board.Board
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import java.io.{Writer, BufferedReader, StringWriter, StringReader}

class ConsoleDisplaySpec extends FunSpec with ShouldMatchers with BeforeAndAfter {

  var in: BufferedReader      = _
  var out: Writer             = _
  var display: ConsoleDisplay = _

  before {
    out     = new StringWriter()
    display = new ConsoleDisplay(in, out)
  }

  def withInStr(str: String, testFn: ConsoleDisplay => Unit) = {
    in = new BufferedReader(new StringReader(str))
    testFn(new ConsoleDisplay(in, out))
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
    }

    describe("Select") {
      val gameType = "game type"
      val options = Vector("Human vs Human", "Human vs Computer")

      it("prints subject") {
        withInStr("", display => {
          display.select(gameType, options)
          out.toString should include(gameType)
        })
      }

      it("prints options") {
        withInStr("", display => {
          display.select(gameType, options)
          out.toString should include(options(0))
          out.toString should include(options(1))
        })
      }

      it("gets the one-indexed selection") {
        withInStr("1\n", display => 
          display.select(gameType, options).get should equal(options(0))
        )
      }

      it("returns None if input is garbage") {
        withInStr("123zzz\n", display =>
          display.select(gameType, options) should not be defined
        )
      }
    }

    describe("getMove") {
      it("asks for move") {
        withInStr("", display =>{
          display.getMove()
          out.toString should include("move")
        })
      }

      it("gets the move with index conversion") {
        withInStr("1\n", display =>
          display.getMove().get should be(0)
        )
      }

      it("move is not defined if input is garbage") {
        withInStr("123zzzz\n", display =>
          display.getMove() should not be defined
        )
      }
    }

    it("notifies of turn") {
      display.notifyTurn("X")

      out.toString should include("X")
    }

    it("notifies of invalid move") {
      display.invalidMove()

      out.toString should include("Invalid")
    }

    it("announces winner") {
      display.announceResult(Some("X"))

      out.toString should include("X")
    }

    it("announces draw") {
      display.announceResult(None)

      out.toString should include("draw")
    }
  }
}
