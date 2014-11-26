package ttt.game

import ttt.mark.Mark
import ttt.board.Board
import ttt.player.Player
import ttt.display.Display
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import org.scalamock.scalatest.MockFactory

object FakePlayer {
  def X(moves: Int*) = {
    new FakePlayer(moves.toList, Mark.X)
  }

  def O(moves: Int*) = {
    new FakePlayer(moves.toList, Mark.O)
  }
}

class FakePlayer(var moves: Seq[Int], private val _mark:String) extends Player {
  def getMove() = {
    val move = moves.headOption
    moves = moves.drop(1)
    move
  }

  def mark: String = _mark
}

class GameSpec extends FunSpec with ShouldMatchers with MockFactory {

  val stubDisplay = stub[Display]
  
  describe("Playing a turn") {
    describe("With valid move") {
      it("plays the move on board") {
        val game = gameWithMoves(0)
        game.playTurn()

        game.board.isValidMove(0) shouldBe false
      }

      it("advances turn after move has been made") {
        val xPlayer = FakePlayer.X(0)
        val oPlayer = FakePlayer.O(1)
        val game = gameWithPlayers(xPlayer, oPlayer)
        game.playTurn()

        game.currentPlayer shouldBe oPlayer
      }

      it("shows the Board") {
        val game = gameWithMoves(0)
        game.playTurn()

        (stubDisplay.showBoard _).verify(*).once
      }
    }

    describe("With invalid move") {
      it("does not play the move") {
        val game = gameWithMoves(99)
        game.playTurn()

        game.board.validMoves should have length 9
      }

      it("does not play a move if move is not defined") {
        val game = gameWithMoves()
        game.playTurn()

        game.board.validMoves should have length 9
      }

      it("does not advance the turn") {
        val xPlayer = FakePlayer.X(99)
        val oPlayer = FakePlayer.O(1)
        val game = gameWithPlayers(xPlayer, oPlayer)
        game.playTurn()

        game.currentPlayer shouldBe xPlayer
      }
    }

    describe("game loop") {
      it("plays through the whole game when X wins") {
        val xPlayer = FakePlayer.X(0,1,2)
        val oPlayer = FakePlayer.O(6,7)
        val game = gameWithPlayers(xPlayer, oPlayer)
        game.play()

        game.winnerMark.get should be(xPlayer.mark)
      }

      it("plays through the whole game when O wins") {
        val xPlayer = FakePlayer.X(5,6,7)
        val oPlayer = FakePlayer.O(0,1,2)
        val game = gameWithPlayers(xPlayer, oPlayer)
        game.play()

        game.winnerMark.get should be(oPlayer.mark)
      }
    }

  }

  def gameWithMoves(moves: Int*) = {
    gameWithPlayers(FakePlayer.X(moves:_*))
  }

  def gameWithPlayers(players: Player*) = {
    new Game(Board.empty, stubDisplay, players.toList)
  }

}
