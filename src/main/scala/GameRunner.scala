package ttt.game_runner

import ttt.board.Board
import ttt.game.Game
import ttt.mark.Mark.{X, O}
import ttt.player.Player
import ttt.display.Display
import ttt.console_display.ConsoleDisplay
import ttt.console_player.ConsolePlayer
import ttt.negamax_player.NegamaxPlayer

import java.io.{PrintWriter, BufferedReader, InputStreamReader}

object GameRunner {
  val in  = new BufferedReader(new InputStreamReader(System.in))
  val out = new PrintWriter(System.out)
  val display = new ConsoleDisplay(in, out)

  def main(args: Array[String]) = {
    new GameRunner(display).playUntilPlayerQuits()
  }

}

class GameRunner(private val display: Display) {
  val gameFor = Map(
    "Human vs Human"       -> { build(human(X), human(O))},
    "Human vs Computer"    -> { build(human(X), computer(O)) },
    "Computer vs Human"    -> { build(computer(X), human(O)) },
    "Computer vs Computer" -> { build(computer(X), computer(O)) }
  )

  def playUntilPlayerQuits() = {
    var playAgain = true
    while(playAgain) {
      gameFor(selectGame()).play()
      playAgain = display.playAgain()
    }
  }

  private def selectGame() = {
    display.select("game type", gameFor.keys.toList).get
  }

  private def build(players: Player*) = {
    new Game(Board.empty, display, players)
  }

  private def human(mark: String) = { 
    new ConsolePlayer(mark, display)
  }

  private def computer(mark: String) = {
    new NegamaxPlayer(mark)
  }
}
