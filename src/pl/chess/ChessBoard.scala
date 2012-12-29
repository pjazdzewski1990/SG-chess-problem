package pl.chess

import pl.chess.pawns.Chessman

/** object representing solutions for the outside world */
class ChessBoard(val board: Array[Array[Chessman]]) {
  /** simple graphical representation of a chess board */
  def draw = {
    board.foldLeft(""){
      (drawn, line) => {
        drawn + line.foldLeft("|"){
          (drawn, figure) => { 
            drawn + Option(figure).getOrElse(" ") 
          }
        } + "|\n"
      }
    }
  }
  
  override def toString = draw
  
  override def equals(arg: Any) = arg match {
    case b:ChessBoard => {
    	/*println("" + b.board(0).deep == this.board(0).deep); 
    	println(b.board.deep)
    	println(this.board.deep)*/
    	b.board.deep == this.board.deep
    }
    case _ => false
  }
}