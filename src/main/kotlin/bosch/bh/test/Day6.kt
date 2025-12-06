import java.io.File
import kotlin.text.toLong

class Day6 {
  fun start() {
    val data1 = File("src/main/resources/day6_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 4277556L) { "expected 4277556 but got $result1" }

    val data2 = File("src/main/resources/day6_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 3263827L) { "expected 3263827 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Long {
    var sum = 0L

    val numbersAndOperators = lines.map { line ->
      line.trim().split(Regex("\\s+"))
    }

    println("numbers $numbersAndOperators")

    val operators = numbersAndOperators.last()

    for (x in operators.indices) {
      val operator = operators[x]
      when (operator) {
        "+" -> {
          var operationResult = 0L
          for (i in 0..numbersAndOperators.size - 2) {
            operationResult += numbersAndOperators[i][x].toLong()
          }
          sum += operationResult
        }

        "*" -> {
          var operationResult = 1L
          for (i in 0..numbersAndOperators.size - 2) {
            operationResult *= numbersAndOperators[i][x].toLong()
          }

          sum += operationResult
        }
      }
    }

    return sum
  }

  private fun part2(lines: List<String>): Long {
    val tiltedList: MutableList<String> = mutableListOf()

    val maxX = lines.maxBy { line -> line.length }.length

    // tilt the list
    for (x in 0 until maxX) {
      val newLine = StringBuilder()
      for (y in lines.indices) {
        val line = lines[y]
        if (x < line.length) {
          newLine.append(line[x])
        } else {
          newLine.append(" ")
        }
      }
      tiltedList.add(newLine.toString())
    }

    tiltedList.add("")

    tiltedList.forEach {
      println(it)
    }

    var currentOperator = ""
    val numbers = mutableListOf<Long>()
    var sum = 0L

    for (line in tiltedList) {
      if (line.isBlank()) {
        if (currentOperator != "") {
          when (currentOperator) {
            "+" -> {
              var operationResult = 0L
              for (number in numbers) {
                operationResult += number
              }
              sum += operationResult
            }

            "*" -> {
              var operationResult = 1L
              for (number in numbers) {
                operationResult *= number
              }
              sum += operationResult
            }
          }
          numbers.clear()
        }
      } else {
        val regex = Regex("[*+]")
        val trimmedLine = line.trim()
        if (line.contains(regex)) {
          currentOperator = trimmedLine.last().toString()
          numbers.add(trimmedLine.dropLast(1).trim().toLong())
        } else {
          numbers.add(trimmedLine.toLong())
        }
      }

    }

    return sum
  }

}

fun main() {
  Day6().start()
}