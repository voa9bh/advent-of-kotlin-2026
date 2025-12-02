import java.io.File

class Day2 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day2_1.txt").readText()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 1227775554L) { "expected 1227775554 but got $result1" }

    val data2 = File("src/main/resources/day2_2.txt").readText()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")
    check(result3 == 4174379265L) { "expected 4174379265 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part2(line: String) : Long {
    val ranges = line.split(",").map { range -> range.split("-") }.map { pair ->
      if(pair[0].startsWith("0") || pair[1].startsWith("0")) {
        println("${pair[0]} or ${pair[1]} is invalid")
        return@map Pair(0L, 0L)
      }
      val start = pair[0].toLong()
      val end = pair[1].toLong()
      Pair(start, end)
    }

    var sum= 0L

    ranges.forEach { range ->
      val start = range.first
      val end = range.second

      for (i in start..end) {
        val numString = i.toString()
        if(!isValidId(numString)) {
          println("$i is invalid")
          sum+=i
        }
      }

      println()

    }

    return sum
  }

  private fun isValidId(id: String): Boolean {
    // Check all possible pattern lengths from 1 to half of the string length
    for (patternLength in 1..id.length / 2) {
      // Only check if the pattern length divides the total length evenly
      if (id.length % patternLength == 0) {
        val pattern = id.substring(0, patternLength)
        val repeated = pattern.repeat(id.length / patternLength)
        if (repeated == id) {
          return false // Found a repeating pattern
        }
      }
    }
    return true // No repeating pattern found
  }


  private fun part1(line: String): Long {
    val ranges = line.split(",").map { range -> range.split("-") }.map { pair ->
      if(pair[0].startsWith("0") || pair[1].startsWith("0")) {
        println("${pair[0]} or ${pair[1]} is invalid")
        return@map Pair(0L, 0L)
      }
      val start = pair[0].toLong()
      val end = pair[1].toLong()
      Pair(start, end)
    }

    var sum= 0L

    ranges.forEach { range ->
      val start = range.first
      val end = range.second

      for (i in start..end) {
        val numString = i.toString()
        val length = numString.length
        if(length % 2==0) {
          val firstHalf = numString.substring(0, numString.length / 2)
          val secondHalf = numString.substring(numString.length / 2, numString.length)
          if(firstHalf == secondHalf) {
            println("$i is invalid")
            sum+=i
          }
        }
      }

      println()

    }

    return sum
  }

}

fun main() {
  Day2().start()
}