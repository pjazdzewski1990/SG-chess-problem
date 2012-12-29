package pl.chess

object Main {

  /** Entry point for program */
  def main(args: Array[String]): Unit = {
    val M = 4; 
    val N = 4;
    val chessSet = Map("kingNum" -> 0,
    				"queenNum" -> 0,
    				"runnerNum"	-> 0,
    				"knightNum"	-> 4,
    				"towerNum" -> 2)
    				
    val task = new ChessProblem(M, N, chessSet)
    val result = task.solve(new BasicAlgorithm())
    println("Possible solutions - " + result.length + ":")
    result.foreach(res => println(res))
  }
}