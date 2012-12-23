package pl.chess.pawns

class Knight extends Chessman {
	override def toString = "H"
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]] = {
	  //val board = Array.fill(xSize, ySize)(false)
	  board
	}
}