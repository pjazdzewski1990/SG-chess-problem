package pl.chess.pawns

class Queen extends Chessman {
	override def toString = "Q"
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]] = {
	  //val board = Array.fill(xSize, ySize)(false)
	  board
	}
}