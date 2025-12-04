package bosch.bh.test.bosch.bh.test

import java.io.File

class Day4 {

  fun start() {
    val data1 = File("src/main/resources/day4_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 13L) { "expected 13 but got $result1" }

    val data2 = File("src/main/resources/day4_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")
    check(result3 == 43L) { "expected 43 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  fun getAt(room: List<String>, x: Int, y: Int): Char {
    if (y < 0 || y >= room.size) {
      return '.'
    }
    if (x < 0 || x >= room[0].length) {
      return '.'
    }
    val row = room[y]
    return row[x]
  }

  private fun part1(room: List<String>): Long {
    var overallCount = 0L

    for (y in room.indices) {
      val row = room[y]
      for (x in row.indices) {
        var paperCount = 0
        for (dy in -1..1) {
          for (dx in -1..1) {
            if (dx == 0 && dy == 0) continue // skip the center cell
            val nx = x + dx
            val ny = y + dy
            val surrounding = getAt(room, nx, ny)
            if (surrounding == '@') {
              paperCount++
            }
          }
        }

        val centerCell=getAt(room,x,y)
        if(centerCell == '.') {
          print(".")
        } else {
          if(paperCount <4) {
            overallCount++
            print("x")
          } else {
            print("@")
          }
        }

      }
      println()

    }
    return overallCount
  }

  private fun countPapers(room: List<String>): Long {
    var overallCount = 0L

    for (y in room.indices) {
      val row = room[y]
      for (x in row.indices) {
        val centerCell=getAt(room,x,y)
        if(centerCell == '@') {
          overallCount++
        }
      }
    }
    return overallCount
  }

  private fun part2(room: List<String>): Long {
    var currentRoom = room
    while(true) {
      val beforeAttempt = countPapers(currentRoom)
      currentRoom = removeAccessiblePaper(currentRoom)
      println("currentRoom:")
      currentRoom.forEach { println(it) }
      println()
      val afterAttempt = countPapers(currentRoom)
      if(afterAttempt==beforeAttempt) {
        break
      }
    }
    return countPapers(room)-countPapers(currentRoom)
  }

  private fun removeAccessiblePaper(room: List<String>): List<String> {
    val returnValue = mutableListOf<String>()

    for (y in room.indices) {
      val row = room[y]
      val newRow = StringBuilder()

      for (x in row.indices) {
        var paperCount = 0
        for (dy in -1..1) {
          for (dx in -1..1) {
            if (dx == 0 && dy == 0) continue // skip the center cell
            val nx = x + dx
            val ny = y + dy
            val surrounding = getAt(room, nx, ny)
            if (surrounding == '@') {
              paperCount++
            }
          }
        }

        val centerCell=getAt(room,x,y)
        if(centerCell == '.') {
          newRow.append(".")
        } else {
          if(paperCount <4) {
            newRow.append(".")
          } else {
            newRow.append("@")
          }
        }

      }
      returnValue.add(newRow.toString())

    }
    return returnValue
  }
}

fun main() {
  Day4().start()
}