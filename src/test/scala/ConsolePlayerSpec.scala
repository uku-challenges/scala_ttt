package ttt.console_player

import ttt.mark.Mark._
import ttt.board.Board
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import java.io.{StringReader, BufferedReader}

class ConsolePlayerSpec extends FunSpec with ShouldMatchers {
  
  def withInStr(str: String, testFn: ConsolePlayer => Unit) = {
    testFn(new ConsolePlayer(X, new BufferedReader(new StringReader(str))))
  }

  describe("Console Player"){
    it("gets a one-indexed move") {
      withInStr("3\n", player =>
          player.getMove(Board.empty).get should equal(2)
       )
    }

    it("move is not defined when input is not a digit") {
      withInStr("bad\n", player =>
          player.getMove(Board.empty) should not be defined
       )
    }
  }
}
