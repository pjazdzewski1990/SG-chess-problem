package pl.chess

import pl.chess.pawns.Chessman

class ChessBoard(board: Array[Array[Chessman]]) {
  def draw = {
    board.foldLeft(""){
      (drawn, line) => {
        drawn + line.foldLeft(""){
          (drawn, figure) => { 
            drawn + Option(figure).getOrElse("") 
          }
        }
      }
    }
  }
}