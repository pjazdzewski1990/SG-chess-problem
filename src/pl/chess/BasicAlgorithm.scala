package pl.chess

import scala.collection.immutable.Map
import pl.chess.pawns.Chessman
import pl.chess.pawns.King
import scala.collection.mutable.MutableList
import pl.chess.pawns.Knight
import pl.chess.pawns.Runner
import pl.chess.pawns.Tower
import pl.chess.pawns.Queen

class BasicAlgorithm extends SolverAlgorithm {

  def solve(sizeX: Int, sizeY: Int, chessSet: Map[String, Int]): List[ChessBoard] = {
    parseArgs(chessSet)
	
	val possibleResults = MutableList[ChessBoard]()
	
	val pieces = createPiecesList()
    var attackBoard = createAttackBoard(sizeX, sizeY)
    
    var firstAvailableField = 0
    var firstNotPlacedPiece : Int = 0
    while(true){
      //set the first piece on the first available position
      val y = Math.floor(firstAvailableField/(sizeX*1.0))
      val test = (pieces(firstNotPlacedPiece)._1, y.toInt, firstAvailableField%sizeX)
      pieces(firstNotPlacedPiece) = (pieces(firstNotPlacedPiece)._1, y.toInt, firstAvailableField%sizeX)
      
      attackBoard = calculateAttackBoard(sizeX, sizeY, pieces)
      
      if(noAttacks(pieces, attackBoard)){
        firstNotPlacedPiece = findNotPlacedPiece(pieces)
        if(firstNotPlacedPiece == -1){
    	  //save soltution on list
          possibleResults += convertToChessboard(sizeX, sizeY, pieces)
        }
      }
    }
    
	possibleResults.toList
  }
  
  def calculateAttackBoard(sizeX: Int, sizeY: Int, pieces: MutableList[(Chessman, Int, Int)]) = {
    var board = Array.fill(sizeX, sizeY)(false)
    pieces.foreach(
      piece => {
        board = piece._1.markAttackPositions(piece._2, piece._3, board)
      }
    )
    board
  }
  
  def findNotPlacedPiece(pieces: MutableList[(Chessman, Int, Int)]) :Int = {
    pieces.indexWhere(piece => -1==piece._2 && -1==piece._3)
  }
  
  def convertToChessboard(sizeX: Int, sizeY: Int, pieces: MutableList[(Chessman, Int, Int)]) = {
    var boardArray = new Array[Array[Chessman]](sizeY);
    for(i <- 0 until sizeY){
      boardArray(i) = new Array[Chessman](sizeX)
    }
    pieces.foreach(
      piece => {
        boardArray(piece._3)(piece._2) = piece._1
      }
    )
    val board = new ChessBoard(boardArray)
    board
  }
  
  def createPiecesList() = {
    val pieces = MutableList[(Chessman, Int, Int)]()
	for(i <- 0 until kingNum){
	  pieces += ((new King(), -1, -1))
	}
    for(i <- 0 until knightNum){
	  pieces += ((new Knight(), -1, -1))
	}
    for(i <- 0 until runnerNum){
	  pieces += ((new Runner(), -1, -1))
	}
    for(i <- 0 until towerNum){
	  pieces += ((new Tower(), -1, -1))
	}
    for(i <- 0 until queenNum){
	  pieces += ((new Queen(), -1, -1))
	}
    pieces
  }
  
  def createAttackBoard(sizeX: Int, sizeY: Int) = {
    Array.fill(sizeX, sizeY)(false)
  }
  
  def noAttacks(pieces: MutableList[(Chessman, Int, Int)], attackBoard: Array[Array[Boolean]]) = {
    pieces.forall(
      piece => 
      attackBoard(piece._3)(piece._2) == false  
    )
  }
}