package pl.chess.pawns

/** Represents chess piece able to attack all adjected fields*/
class King extends Chessman {
	override def toString = "K" // it's a pity but we cannot use utf-8 chess characters
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]] = {
	  val xSize = board(0).length
	  val ySize = board.length
	  
	  board(yPos)(xPos) = true
	  
	  //mark upper part 
	  if(yPos-1 >= 0){
	    board(yPos-1)(xPos) = true
	    if(xPos-1 >= 0){
	      board(yPos-1)(xPos-1) = true
	    }
	    if(xPos+1 < xSize){
	      board(yPos-1)(xPos+1) = true
	    }
	  }
	  //mark lower part 
	  if(yPos+1 < ySize){
	    board(yPos+1)(xPos) = true
	    if(xPos-1 >= 0){
	      board(yPos+1)(xPos-1) = true
	    }
	    if(xPos+1 < xSize){
	      board(yPos+1)(xPos+1) = true
	    }
	  }
	  
	  //mark sides
	  if(xPos-1 >= 0){
	    board(yPos)(xPos-1) = true
	  }
	  if(xPos+1 < xSize){
	    board(yPos)(xPos+1) = true
	  }
	  
	  board
	}
}