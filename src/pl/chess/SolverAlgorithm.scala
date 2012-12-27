package pl.chess

/** classes extending SolverAlgorithm are able to work as a engine for the chess problem - are able to come with a solution
 * It also provides methods usefull across all solverAlgorithm classes*/
abstract class SolverAlgorithm {
	
	var kingNum  	= 0
    var queenNum 	= 0
    var runnerNum	= 0
    var knightNum	= 0
    var towerNum	= 0
	protected def parseArgs(params: Map[String, Int]) = {
	  kingNum = params.getOrElse("kingNum", 0)
	  queenNum = params.getOrElse("queenNum", 0)
	  runnerNum = params.getOrElse("runnerNum", 0)
	  knightNum = params.getOrElse("knightNum", 0)
	  towerNum = params.getOrElse("towerNum", 0)
	}
  
	/** Get all posssible solutions for chessboard size sizeX*sizeY and chessmates from chessSet*/
	def solve(sizeX: Int, sizeY: Int, chessSet: Map[String, Int]): List[ChessBoard]
}