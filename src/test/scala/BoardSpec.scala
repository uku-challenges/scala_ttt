package ttt.board

import ttt.mark.Mark._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class BoardSpec extends FunSpec with ShouldMatchers {

  var board = Board.empty()

  describe("Board") {
    it("makes a move") {
      val marked = board.markSquare(0, X)
      marked.squareAt(0) should equal(X)
    }

    it("empty square is a valid move") {
      board.isValidMove(0) should be(true) 
    }

    it("played square is an invalid move") {
      val marked = board.markSquare(0, O)
      marked.isValidMove(0) should be(false) 
    }

    describe("terminal states") {
      it("empty board has no winner") {
        board.winner should not be defined
      }

      it("empty board has no draw") {
        board.isDraw should be(false)
      }

      it("empty board is not over") {
        board.isOver should be(false)
      }

      describe("Horizontal win") {
        it("first row has winner") {
          val winBoard = Board.make(X, X, X,
                                    3, 4, 5,
                                    6, 7, 8)

          winBoard.winner shouldBe defined
        }

        it("second row has winner") {
          val winBoard = Board.make(0, 1, 2,
                                    X, X, X,
                                    6, 7, 8)

          winBoard.winner shouldBe defined
        }

        it("third row has winner") {
          val winBoard = Board.make(0, 1, 2,
                                    3, 4, 5,
                                    X, X, X)

          winBoard.winner shouldBe defined
        }
      }

      describe("Vertical win") {
        it("first column has winner") {
          val winBoard = Board.make(X, 1, 2,
                                    X, 4, 5,
                                    X, 7, 8)

          winBoard.winner shouldBe defined
        }

        it("second column has winner") {
          val winBoard = Board.make(0, X, 2,
                                    3, X, 5,
                                    6, X, 8)

          winBoard.winner shouldBe defined
        }

        it("third column has winner") {
          val winBoard = Board.make(0, 1, X,
                                    3, 4, X,
                                    6, 7, X)

          winBoard.winner shouldBe defined
        }
      }

      describe("Diagonal win") {
        it("first diagonal has winner") {
          val winBoard = Board.make(X, 1, 2,
                                    3, X, 5,
                                    6, 7, X)

          winBoard.winner shouldBe defined
        }

        it("second diagonal has winner") {
          val winBoard = Board.make(0, 1, X,
                                    3, X, 5,
                                    X, 7, 8)

          winBoard.winner shouldBe defined
        }
      }

      describe("Won game") {
        val xWins = Board.make(X, X, X,
                               3, 4, 5,
                               6, 7, 8)

        val oWins = Board.make(O, O, O,
                               3, 4, 5,
                               6, 7, 8)


        it("first row winner is correct for X") {
          xWins.winner.get shouldEqual X
        }

        it("first row winner is correct for O") {
          oWins.winner.get shouldEqual O
        }

        it("is over when X wins") {
          xWins.isOver shouldBe true
        }

        it("is over when O wins") {
          oWins.isOver shouldBe true
        }
      }

      describe("Draw game")  {
        val drawBoard =  Board.make(O, O, X,
                                    X, O, O,
                                    O, X, X)
 
        it("winner is none") {
          drawBoard.winner should not be defined
        }

        it("draw is true") {
          drawBoard.isDraw should be(true)
        }

        it("draw board is over") {
          drawBoard.isOver should be(true)
        }
      }
    }
  }
}
