package pl.chess.pawns

class King extends Chessman {
	override def toString = "K" // it's a pity but we cannot use utf-8 chess characters
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]] = {
	  //val board = Array.fill(xSize, ySize)(false)
	  board
	}
}