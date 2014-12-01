package ttt

import java.io.{BufferedReader, StringReader, StringWriter, Writer}

import org.scalatest._
import ttt.display.ConsoleDisplay
import ttt.players.{Player, NegamaxPlayer, ConsolePlayer}

class MultipleGameRunnerSpec extends FunSpec with Matchers with BeforeAndAfter{

  var out: Writer  = _

  before {
    out = new StringWriter()
  }

  def withInStr(str: String, testFn: MultipleGameRunner => Unit) = {
    val in = new BufferedReader(new StringReader(str))
    testFn(new MultipleGameRunner(new ConsoleDisplay(in, out)))
  }

  def selectPlayers(selection: String, testFn: Seq[Player] => Unit) = {
    val in = new BufferedReader(new StringReader(selection))
    val runner = new MultipleGameRunner(new ConsoleDisplay(in, out))
    testFn(runner.selectPlayers())
  }

  describe("selecting players") {
    it("human vs human") {
      selectPlayers("1\n", players => {
        players.head      shouldBe a [ConsolePlayer]
        players.tail.head shouldBe a [ConsolePlayer]
      })
    }

    it("human vs computer") {
      selectPlayers("2\n", players => {
        players.head      shouldBe a [ConsolePlayer]
        players.tail.head shouldBe a [NegamaxPlayer]
      })
    }

    it("computer vs human") {
      selectPlayers("3\n", players => {
        players.head      shouldBe a [NegamaxPlayer]
        players.tail.head shouldBe a [ConsolePlayer]
      })
    }

    it("computer vs computer") {
      selectPlayers("4\n", players => {
        players.head      shouldBe a [NegamaxPlayer]
        players.tail.head shouldBe a [NegamaxPlayer]
      })
    }

    it("gets input until valid selection is given") {
      selectPlayers("bad\n99\n\n4\n", players => {
        players.head      shouldBe a [NegamaxPlayer]
        players.tail.head shouldBe a [NegamaxPlayer]
      })
    }
  }

  describe("game runner") {
    it("plays through one game") {
      withInStr("1\n1\n2\n3\n4\n5\n6\n7\nno\n", runner => {
        runner.playUntilPlayerQuits()
        out.toString should include("X has won")
      })
    }

    it("plays through two games") {
      withInStr("1\n1\n2\n3\n4\n5\n6\n7\ny\n1\n1\n2\n3\n4\n5\n6\n7\nno\n", runner => {
        runner.playUntilPlayerQuits()
        out.toString should include("X has won")
      })
    }
  }
}
