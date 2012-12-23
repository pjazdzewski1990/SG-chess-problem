package pl.chess

trait SolverAlgorithm {
	/** Getr all posssible solutions for chessboard size sizeX*sizeY and chessmates from chessSet*/
	def solve(sizeX: Int, sizeY: Int, chessSet: Map[String, Int]): List[ChessBoard]
}