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
	
	val pieces = createPiecesList()
    /*var attackBoard = createAttackBoard(sizeX, sizeY)
    
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
    	  //save solution on list
          possibleResults += convertToChessboard(sizeX, sizeY, pieces)
        }
      }else{
      }
    }*/
    
    //var pieceSkip = 0
    var fieldSkip = 0
    while(true){
      var attackBoard = calculateAttackBoard(sizeX, sizeY, pieces)
      
      val filtered = pieces.zipWithIndex.filter(piece => piece._1._2 == -1) 
      if(filtered.length == 0){
        val solution = convertToChessboard(sizeX, sizeY, pieces)
        println(solution.toString)
        possibleResults += solution
        
        stepBack()
      }
      
      val field = availableField(attackBoard, fieldSkip)
      //val piece = notPlacedPiece(pieces, pieceSkip)
      println("Pole " + field)
      if(field != -1) {
        var fieldSkipIncrement = 0
        //we have found empty not attacked field, now place a figure there
        breakable {
        for(pieceWithIndex <- filtered) {
          val piece = pieceWithIndex._1  
          val index = pieceWithIndex._2
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
          }else{
            //pieceSkip = 0 
            fieldSkipIncrement = 0
            break()
          }
      	}}
      	fieldSkip += fieldSkipIncrement
      	//println("after=" + convertToChessboard(sizeX, sizeY, pieces).toString);
      } else {
        //there is no field to put figure on
        stepBack()
      }
    }
    possibleResults.toList
  }
  
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
  
  def createAttackBoard(sizeX: Int, sizeY: Int) = {
    Array.fill(sizeX, sizeY)(false)
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
  
  def findNotPlacedPiece(pieces: MutableList[(Chessman, Int, Int)]) :Int = {
    pieces.indexWhere(piece => -1==piece._2 && -1==piece._3)
  }
  
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
  
  def notPlacedPiece(pieces: MutableList[(Chessman, Int, Int)], pieceSkip: Int) :Option[(Int,(Chessman, Int, Int))] = {
    var counter = pieceSkip
    var num = -1
    pieces.foreach( piece => { 
        num += 1
      	if(-1==piece._2){
        counter -= 1
        if(counter <= 0){
          return Some((num, piece))
        }
      }
    })
    None
  }
  
  def stepBack() = {
    //TODO: revert last move
  }
}