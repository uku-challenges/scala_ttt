package ttt.console_player

import org.scalamock.scalatest.MockFactory
import org.scalatest._
import ttt.board.Board
import ttt.display.Display
import ttt.mark.Mark._

class ConsolePlayerSpec extends FunSpec with Matchers with MockFactory {
  
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
