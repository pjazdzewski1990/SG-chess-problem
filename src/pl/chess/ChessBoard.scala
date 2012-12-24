package pl.chess

import pl.chess.pawns.Chessman

class ChessBoard(board: Array[Array[Chessman]]) {
  def draw = {
    board.foldLeft(""){
      (drawn, line) => {
        drawn + line.foldLeft("|"){
          (drawn, figure) => { 
            //println(""+drawn + Option(figure).getOrElse(" "))
            drawn + Option(figure).getOrElse(" ") 
          }
        } + "|\n"
      }
    }
  }
  override def toString = draw
}