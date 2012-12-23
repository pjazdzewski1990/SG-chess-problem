package pl.chess

class ChessSolver(sizeX: Int, sizeY: Int, chessSet: Map[String, Int]) {
	def solve(solver: SolverAlgorithm) = solver.solve(sizeX, sizeY, chessSet)
}