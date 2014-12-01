package ttt.game_runner

import ttt.display.Display
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import org.scalamock.scalatest.MockFactory

class GameRunnerSpec extends FunSpec with ShouldMatchers with MockFactory {

  val stubDisplay = stub[Display]

  describe("playing multiple games") {
  }
}
