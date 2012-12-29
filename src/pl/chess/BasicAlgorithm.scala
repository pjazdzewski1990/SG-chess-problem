package pl.chess

import scala.collection.immutable.Map
import scala.collection.mutable.MutableList
import scala.util.control.Breaks._

import pl.chess.pawns.Chessman
import pl.chess.pawns.King
import pl.chess.pawns.Knight
import pl.chess.pawns.Runner
import pl.chess.pawns.Tower
import pl.chess.pawns.Queen

/** Simplest way to solve the riddle, trying out any possible combinations. CHESS PIECES ARE TREATED AS DISTINCT */
class BasicAlgorithm extends SolverAlgorithm {

  def solve(sizeX: Int, sizeY: Int, chessSet: Map[String, Int]): List[ChessBoard] = {
    parseArgs(chessSet)
	var pieces = createPiecesList()
    
	val results = findSolutions(sizeX, sizeY, pieces, 0)
	
    results.toList
  }
  
  def findSolutions(sizeX: Int, sizeY: Int, pieces :MutableList[(Chessman, Int, Int)], firstField: Int) :MutableList[ChessBoard] ={
	  var fieldSkip = 0
	  var attackBoard = calculateAttackBoard(sizeX, sizeY, pieces)
	  val possibleResults = MutableList[ChessBoard]()
      
      val filtered = pieces.zipWithIndex.filter(piece => piece._1._2 == -1) 
      if(filtered.length == 0) {
        val solution = convertToChessboard(sizeX, sizeY, pieces)
        //println(solution.toString)
        
        return MutableList(solution)
      }
      
	  while(true) {
	    val field = availableField(attackBoard, fieldSkip)
		if(field != -1) {
		  //all fields before field num "firstField" are considered as occupied
		  if(field >= firstField){
          //we have found empty not attacked field, now place a figure there
            for(pieceWithIndex <- filtered) {
              val piece = pieceWithIndex._1  
              val index = pieceWithIndex._2
              //there is a piece that can be put on 
              val y = Math.floor(field/sizeX.toDouble)
              val x = field%sizeX
          
              val attackBoard = calculateAttackBoard(sizeX, sizeY, pieces)
              val p = copyList(pieces)
              p(index) = (piece._1, y.toInt, x)
              //there are no attacks:
              // the new figure is not attacked by the old ones && the new one doesn't attack any of the old
              if(attackBoard(y.toInt)(x)==false && !noAttack(sizeX, sizeY, index, p)){
                //revert the changes
              } else {
                //continue looking for a solution
                possibleResults ++= findSolutions(sizeX, sizeY, p, field+1)
              }
            }
		  }
      	  fieldSkip += 1
        } else {
          //there is no field to put figure on
          return possibleResults
        }
	  }
    possibleResults
  }
  
  /** get field ready for putting a chess piece */
  def availableField(attackBoard: Array[Array[Boolean]], fieldSkip: Int) :Int = {
    var counter = fieldSkip
    var elem = 0
    attackBoard.foreach(
      row => row.foreach(
        field => if(!field){
          counter -= 1
          if(counter < 0){
            return elem
          }else{
            elem += 1
          }
        }else{
          elem += 1
        }
      )
    )
    -1
  }
  
  /** return 2D array containing info about attacked positions */
  def calculateAttackBoard(sizeX: Int, sizeY: Int, pieces: MutableList[(Chessman, Int, Int)]) = {
    var board = Array.fill(sizeX, sizeY)(false)
    pieces.foreach(
      piece => {
        if(piece._2 != -1 &&  piece._3 != -1){
        	board = piece._1.markAttackPositions(piece._3, piece._2, board)
        }
      }
    )
    board
  }
  
  def copyList(list: MutableList[(Chessman, Int, Int)]) = {
    val newList = MutableList[(Chessman, Int, Int)]()
    for(i <- list){
      newList += ((i._1, i._2, i._3))
    }
    newList
  }
  
  /** turn list into ChessBoard object */
  def convertToChessboard(sizeX: Int, sizeY: Int, pieces: MutableList[(Chessman, Int, Int)]) = {
    var boardArray = new Array[Array[Chessman]](sizeY);
    for(i <- 0 until sizeY){
      boardArray(i) = new Array[Chessman](sizeX)
    }
    pieces.foreach(
      piece => {
        boardArray(piece._2)(piece._3) = piece._1
      }
    )
    val board = new ChessBoard(boardArray)
    board
  }
  
  /** helper method for creating starting attackBoard */
  def createAttackBoard(sizeX: Int, sizeY: Int) = {
    Array.fill(sizeX, sizeY)(false)
  }
  
  /** create list of chess pieces for current riddle */ 
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
  
  /** check whether the new chess figure, pieces(index), is attacking any pieces */
  def noAttack(sizeX: Int, sizeY: Int, index: Int, pieces: MutableList[(Chessman, Int, Int)]) = {
    val attackBoard = calculateAttackBoard(sizeX, sizeY, MutableList(pieces(index)))
    pieces.zipWithIndex.forall(pieceWithIndex => {
      val piece = pieceWithIndex._1
      if(piece._2 != -1 && pieceWithIndex._2 != index){
        attackBoard(piece._2)(piece._3) == false
      }else{
        true
      }
    })
  }
}