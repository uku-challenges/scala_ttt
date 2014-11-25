package ttt.player

trait Player {
  def getMove(): Option[Int]
  def mark: String
}
