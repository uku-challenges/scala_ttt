package ttt.display

object Ansi {
  private val CLS     = "\u001b[H\u001b[2J"
  private val GREEN   = "\u001b[2;32m"
  private val YELLOW  = "\u001b[2;33m"
  private val RESET   = "\u001b[0m"

  def green(str: String) = {
    colored(str, GREEN)
  }

  def yellow(str: String) = {
    colored(str, YELLOW)
  }

  def clear = CLS

  private def colored(str: String, color: String) = {
    color + str + RESET
  }
}
