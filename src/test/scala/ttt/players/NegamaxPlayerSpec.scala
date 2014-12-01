package ttt.players

import ttt.Board
import ttt.Mark.{X, O}

import org.scalatest._
import org.scalatest.matchers.{MatchResult, Matcher}

class NegamaxPlayerSpec extends FunSpec with Matchers {
  
  def player(mark: String) = {
    new NegamaxPlayer(mark)
  }

  describe("optimal play") {
    it("takes win if can take win") {
      val board = Board.make(X, 1, 2,
                             O, X, 5,
                             O, 7, 8) 

      player(X).getMove(board).get shouldBe 8
    }

    it("defends against certain loss") {
      val board = Board.make(X, 1, 2,
                             X, O, 5,
                             O, 7, 8) 

      player(X).getMove(board).get shouldBe 2
    }

    it("defends against diagonal fork") {
      val board = Board.make(0, 1, X,
                             3, O, 5,
                             X, 7, 8) 

      player(O).getMove(board).get should beIn(1, 3, 5, 7)
    }

    it("defends against alternative diagonal fork") {
      val board = Board.make(0, 1, X,
                             3, X, 5,
                             O, 7, 8) 

      player(O).getMove(board).get should beIn(0, 8)
    }

    it("defends against edge fork") {
      val board = Board.make(0, 1, 2,
                             3, O, X,
                             6, X, 8) 

      player(O).getMove(board).get should beIn(2, 6, 8)
    }
  }

  def beIn(expected: Int*) = new MembershipMatcher(expected.toList)

  class MembershipMatcher(expectedColl: List[Int]) extends Matcher[Int] {
    def apply(actual: Int) = {
      MatchResult(
        expectedColl.contains(actual),
        actual.toString + " was not in " + expectedColl.toString,
        actual.toString + " was in " + expectedColl.toString
      )
    }
  }
}
