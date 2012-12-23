package pl.chess.pawns

/** represents chess figure able to move vertically and horizontally in straight lines */
class Tower extends Chessman {
	override def toString = "T"
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]) :Array[Array[Boolean]] = {
	  val xSize = board(0).length
	  val ySize = board.length
	  
	  //mark horizontal attack 
	  board(yPos) = Array.fill(xSize)(true)
	  //mark horizontal attack 
	  for(i <- 0 until ySize){
	    board(i)(xPos) = true
	  }
	  
	  board
	}
}