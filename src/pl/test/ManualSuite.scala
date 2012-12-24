package pl.test

import pl.chess.pawns.Tower
import pl.chess.pawns.Runner
import pl.chess.pawns.Queen
import pl.chess.pawns.Knight
import pl.chess.pawns.King

object ManualSuite {

  def main(args: Array[String]): Unit = {
    val towerMark = new Tower().markAttackPositions(0, 0, Array.fill(5,5)(false))
    val runnerMark = new Runner().markAttackPositions(1, 2, Array.fill(5,5)(false))
    val queenMark = new Queen().markAttackPositions(1, 1, Array.fill(5,5)(false))
    val knightMark = new Knight().markAttackPositions(2, 2, Array.fill(5,5)(false))
    val kingMark = new King().markAttackPositions(3, 3, Array.fill(5,5)(false))
  }

}