package ttt.game_runner

import ttt.console_display.ConsoleDisplay
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import org.scalamock.scalatest.MockFactory
import java.io.{Writer, BufferedReader, StringWriter, StringReader}

class GameRunnerSpec extends FunSpec with ShouldMatchers with BeforeAndAfter{

  var out: Writer  = _

  before {
    out = new StringWriter()
  }

  def withInStr(str: String, testFn: GameRunner => Unit) = {
    val in = new BufferedReader(new StringReader(str))
    testFn(new GameRunner(new ConsoleDisplay(in, out)))
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
