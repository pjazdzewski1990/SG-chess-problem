package pl.chess

import pl.chess.pawns.Chessman

/** object representing solutions for the outside world */
class ChessBoard(board: Array[Array[Chessman]]) {
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
}