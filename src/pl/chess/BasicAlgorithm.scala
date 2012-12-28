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

class BasicAlgorithm extends SolverAlgorithm {

  def solve(sizeX: Int, sizeY: Int, chessSet: Map[String, Int]): List[ChessBoard] = {
    parseArgs(chessSet)
	
	val possibleResults = MutableList[ChessBoard]()
	
	var pieces = createPiecesList()
    var index = 0		//id of last moved
	
    var fieldSkip = 0
    while(true){
      var attackBoard = calculateAttackBoard(sizeX, sizeY, pieces)
      
      val filtered = pieces.zipWithIndex.filter(piece => piece._1._2 == -1) 
      if(filtered.length == 0){
        val solution = convertToChessboard(sizeX, sizeY, pieces)
        println(solution.toString)
        possibleResults += solution
        
        pieces = stepBack(index, pieces)
      }
      
      val field = availableField(attackBoard, fieldSkip)
      if(field != -1) {
        var fieldSkipIncrement = 0
        //we have found empty not attacked field, now place a figure there
        breakable {
          for(pieceWithIndex <- filtered) {
            val piece = pieceWithIndex._1  
            index = pieceWithIndex._2
            //there is a piece that can be put on 
            val y = Math.floor(field/sizeX.toDouble)
            val x = field%sizeX
          
            val oldY = piece._2
            val oldX = piece._3
          
            val attackBoard = calculateAttackBoard(sizeX, sizeY, pieces)
            pieces(index) = (piece._1, y.toInt, x)
            //there are no attacks:
            // the new figure is not attacked by the old ones && the new one doesn't attack any of the old
            if(attackBoard(y.toInt)(x)==false && !noAttack(sizeX, sizeY, index, pieces)){
              //revert the changes
              //pieceSkip += 1
              fieldSkipIncrement = 1
              pieces(index) = (piece._1, oldY, oldX)
            } else {
              //pieceSkip = 0 
              fieldSkipIncrement = 0
              break()
            }
      	  }
        }
      	fieldSkip += fieldSkipIncrement
      } else {
        //there is no field to put figure on
        pieces = stepBack(index, pieces)
        index -= 1
        fieldSkip += 1
      }
    }
    possibleResults.toList
  }
  
  /** get field ready for putting a chess piece */
  def availableField(attackBoard: Array[Array[Boolean]], fieldSkip: Int) :Int = {
    var counter = fieldSkip
    var elem = 0
    attackBoard.foreach(
      row => row.foreach(
        field => if(!field){
          counter -= 1
          if(counter <= 0){
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
  
  def stepBack(index :Int, pieces :MutableList[(Chessman, Int, Int)]) = {
    //reset position the element at "index"
    // ... and put that piece at the end of list
    val test = pieces.take(index) ++ pieces.drop(index+1) ++ List((pieces(index)._1, -1, -1))
    test
  }
}