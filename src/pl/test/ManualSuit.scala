package pl.test

import pl.chess.pawns.Tower
import pl.chess.pawns.Runner

object ManualSuit {

  def main(args: Array[String]): Unit = {
    val towerMark = new Tower().markAttackPositions(0, 0, Array.fill(5,5)(false))
    println(towerMark)
    val runnerMark = new Runner().markAttackPositions(1, 2, Array.fill(5,5)(false))
    println(runnerMark)
  }

}