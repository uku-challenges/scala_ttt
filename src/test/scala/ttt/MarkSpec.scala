package ttt

import org.scalatest._

class MarkSpec extends FunSpec with Matchers {

  describe("isMark") {
    it("X is a mark") {
      Mark.isMark(Mark.X) should be(true)
    }

    it("O is a mark") {
      Mark.isMark(Mark.O) should be(true)
    }

    it("other objects are not marks") {
      Mark.isMark("Not a mark") should be(false)
      Mark.isMark(5) should be(false)
    }
  }

  describe("opponentOf") {
    it("X's opponent is O") {
      Mark.opponentOf(Mark.X) should be(Mark.O)
    }

    it("O's opponent is X") {
      Mark.opponentOf(Mark.O) should be(Mark.X)
    }
  }
}
