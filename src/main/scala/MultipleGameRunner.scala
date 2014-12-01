package ttt.multiple_game_runner

import ttt.board.Board
import ttt.game.Game
import ttt.mark.Mark.{X, O}
import ttt.player.Player
import ttt.display.Display
import ttt.console_display.ConsoleDisplay
import ttt.console_player.ConsolePlayer
import ttt.negamax_player.NegamaxPlayer

import java.io.{PrintWriter, BufferedReader, InputStreamReader}

object MultipleGameRunner {
  val in  = new BufferedReader(new InputStreamReader(System.in))
  val out = new PrintWriter(System.out)
  val display = new ConsoleDisplay(in, out)

  def main(args: Array[String]) = {
    new MultipleGameRunner(display).playUntilPlayerQuits()
  }
}

class MultipleGameRunner(private val display: Display) {
  private val playerTypes = Map(
    "Human vs Human"       -> List(human(X), human(O)),
    "Human vs Computer"    -> List(human(X), computer(O)),
    "Computer vs Human"    -> List(computer(X), human(O)),
    "Computer vs Computer" -> List(computer(X), computer(O))
  )

  def playUntilPlayerQuits() = {
    var playAgain = true
    while(playAgain) {
      selectGame.play()
      playAgain = display.playAgain()
    }
  }

  def selectPlayers() = {
    var selection: Option[String] = None
    while(!selection.isDefined) {
      selection = display.select("players", playerTypes.keys.toList)
    }
    playerTypes(selection.get)
  }

  private def selectGame = {
    build(selectPlayers())
  }

  private def build(players: Seq[Player]) = {
    new Game(Board.empty, display, players)
  }

  private def human(mark: String) = { 
    new ConsolePlayer(mark, display)
  }

  private def computer(mark: String) = {
    new NegamaxPlayer(mark)
  }
}
