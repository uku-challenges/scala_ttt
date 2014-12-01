package ttt.players

import org.scalamock.scalatest.MockFactory
import org.scalatest._
import ttt.Board
import ttt.Mark.X
import ttt.display.Display

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
