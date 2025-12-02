import java.io.File

class Day1 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day1_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 3) { "expected 3 but got $result1" }

    val data2 = File("src/main/resources/day1_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")
    check(result3 == 6) { "expected 6 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
    check(result4 == 6770) { "expected 6770 but got $result4" }
  }

  private fun part1(lines: List<String>): Int {
    var position = 50

    var passesOf0 = 0

    for (line in lines) {
      val dir = line[0]
      val steps = line.substring(1).toInt()
      when (dir) {
        'L' -> position -= steps
        'R' -> position += steps
      }
      while(position > 99) {
        position -= 100
      }

      while(position < 0) {
        position += 100
      }

      println(position)

      if(position == 0) {
        passesOf0++
      }
    }
    return passesOf0
  }

  private fun part2(lines: List<String>): Int {
    var position = 50
    var passesOf0 = 0

    for (line in lines) {
      val dir = line[0]
      val steps = line.substring(1).toInt()

      if (dir == 'L') {
        repeat(steps) {
          position--
          if (position < 0) position += 100
          if (position == 0) passesOf0++
        }
      } else if (dir == 'R') {
        repeat(steps) {
          position++
          if (position > 99) position -= 100
          if (position == 0) passesOf0++
        }
      }

      println("$position $passesOf0")
    }
    return passesOf0
  }

}

fun main() {
  Day1().start()
}