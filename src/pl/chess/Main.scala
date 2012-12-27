package pl.chess

object Main {

  /** Entry point for program */
  def main(args: Array[String]): Unit = {
    val M = 3; 
    val N = 3;
    val chessSet = Map("kingNum" -> 2,
    				"queenNum" -> 0,
    				"runnerNum"	-> 0,
    				"knightNum"	-> 0,
    				"towerNum" -> 1)
    				
    val basic = new BasicAlgorithm();
    val task = new ChessProblem(M, N, chessSet)
    val result = task.solve(basic)
    result.foreach(res => println(res))
  }
}