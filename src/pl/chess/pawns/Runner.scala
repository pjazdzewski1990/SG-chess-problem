package pl.chess.pawns

/** Represents Chess figure able to attack in diagonal lines */
class Runner extends Chessman {
	override def toString = "R"
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]] = {
	  val xSize = board(0).length
	  val ySize = board.length
	  
	  //TODO: merge for loops
	  //mark everything left from piece position
	  var diff = 0
	  for(x <- xPos until -1 by -1){
	    if(yPos-diff >= 0){
	    	board(yPos-diff)(x) = true
	    }
	    if(yPos+diff < ySize){
	    	board(yPos+diff)(x) = true
	    }
	    diff+=1
	  }
	  //mark everything right from piece position
	  diff = 0
	  for(x <- xPos until xSize){
	    if(yPos-diff >= 0){
	    	board(yPos-diff)(x) = true
	    }
	    if(yPos+diff < ySize){
	    	board(yPos+diff)(x) = true
	    }
	    diff+=1
	  }
	   
	  board
	}
}