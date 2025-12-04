package bosch.bh.test.bosch.bh.test

import java.io.File

class Day3 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day3_1.txt").readLines()
    val result1 = part1(data1, 2)
    println("result1 = $result1")

    check(result1 == 357L) { "expected 357 but got $result1" }

    val data2 = File("src/main/resources/day3_2.txt").readLines()
    val result2 = part1(data2,2)
    println("result2 = $result2")

    val result3 = part1(data1, 12)
    println("result3 = $result3")
    check(result3 == 3121910778619L) { "expected 3121910778619 but got $result3" }

    val result4 = part1(data2,12)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>, numDigits: Int): Long {
    var sum = 0L

    for (line in lines) {
      val lineMax = findMaxSequence(line, numDigits)
      sum += lineMax
    }

    return sum

  }

  private fun findMaxSequence(input: String, numDigits: Int): Long {
    if (numDigits > input.length) return input.toLong()

    val result = StringBuilder()
    var remaining = numDigits
    var startIndex = 0

    while (remaining > 0) {
      val windowEnd = input.length - remaining + 1
      var maxDigit = '0'
      var maxIndex = startIndex

      for (i in startIndex until windowEnd) {
        if (input[i] > maxDigit) {
          maxDigit = input[i]
          maxIndex = i
        }
      }

      result.append(maxDigit)
      startIndex = maxIndex + 1
      remaining--
    }

    return result.toString().toLong()
  }

}

fun main() {
  Day3().start()
}
