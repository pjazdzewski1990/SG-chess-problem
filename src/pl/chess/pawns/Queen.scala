package pl.chess.pawns

/** Chess piece able to attack diagonally and in straight lines*/
class Queen extends Chessman {
	override def toString = "Q"
	override def markAttackPositions(xPos: Int, yPos: Int, board: Array[Array[Boolean]]): Array[Array[Boolean]] = {
	  val xSize = board(0).length
	  val ySize = board.length
	  
	  //mark horizontal attack 
	  board(yPos) = Array.fill(xSize)(true)
	  //TODO: merge for loops
	  //mark horizontal attack 
	  for(i <- 0 until ySize){
	    board(i)(xPos) = true
	  }
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