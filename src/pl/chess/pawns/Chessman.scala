package pl.chess.pawns

/** Abstract class for pawn. All "usefull" chess figures will be extending this class */
abstract class Chessman {
  override def toString = " "
  def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]]
}