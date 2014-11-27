package ttt.console_player

import ttt.mark.Mark._
import ttt.board.Board
import ttt.display.Display

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import org.scalamock.scalatest.MockFactory
import java.io.{StringReader, BufferedReader}

class ConsolePlayerSpec extends FunSpec with ShouldMatchers with MockFactory {
  
  val display = mock[Display]
  val player = new ConsolePlayer(X, display)

  describe("console player") {
    it("gets move from display") {
      (display.getMove _).expects().returning(Some(4))
      val move = player.getMove(Board.empty)

      move.get should be(4)
    }
  }
}
