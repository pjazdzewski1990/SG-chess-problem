package pl.chess.pawns

/** Chess figure able to attack by moving 2 steps in one direction and the turning left/right */
class Knight extends Chessman {
	override def toString = "H"
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]] = {
	  val xSize = board(0).length
	  val ySize = board.length
	  
	  board(yPos)(xPos) = true
	  
	  //TODO: try to merge
	  //mark upper part
	  if(yPos-2 >= 0){
	    if(xPos-1 >= 0){
	      board(xPos-1)(yPos-2) = true
	    }
	    if(xPos+1 < xSize){
	      board(xPos+1)(yPos-2) = true
	    }
	  }
	  
	  //mark lower part
	  if(yPos+2 < ySize){
	    if(xPos-1 >= 0){
	      board(xPos-1)(yPos+2) = true
	    }
	    if(xPos+1 < xSize){
	      board(xPos+1)(yPos+2) = true
	    }
	  }
	  
	  //mark left part
	  if(xPos-2 >= 0){
	    if(yPos-1 >= 0){
	      board(xPos-2)(yPos-1) = true
	    }
	    if(yPos+1 < ySize){
	      board(xPos-2)(yPos+1) = true
	    }
	  }
	  	  
	  //mark right part
	  if(xPos+2 < xSize){
	    if(yPos-1 >= 0){
	      board(xPos+2)(yPos-1) = true
	    }
	    if(yPos+1 < ySize){
	      board(xPos+2)(yPos+1) = true
	    }
	  }
	  
	  board
	}
}