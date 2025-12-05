package bosch.bh.test.bosch.bh.test

import java.io.File

class Day5 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day5_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 3L) { "expected 3 but got $result1" }

    val data2 = File("src/main/resources/day5_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 14L) { "expected 14 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Long {
    var index = 0

    val ranges = mutableListOf<LongRange>()

    while (index < lines.size) {
      val line = lines[index]

      println("line: $line")

      if(line.isBlank()) {
        break
      }

      val splitLine=line.split("-")
      val start = splitLine[0].toLong()
      val end = splitLine[1].toLong()
      val range = start..end

      ranges.add(range)

      index++
    }

    var sum = 0L
    index++ // skip blank line

    while(index < lines.size) {
      val line = lines[index]

      val value = line.toLong()

      val fresh = ranges.any { range -> value in range }

      if(fresh) {
        sum++
      }

      index++
    }

    return sum
  }

  private fun part2(lines: List<String>): Long {
    var index = 0

    val ranges = mutableListOf<LongRange>()

    while (index < lines.size) {
      val line = lines[index]

      println("line: $line")

      if(line.isBlank()) {
        break
      }

      val splitLine=line.split("-")
      val start = splitLine[0].toLong()
      val end = splitLine[1].toLong()
      val range = start..end

      ranges.add(range)

      index++
    }


    val start = ranges.minBy { range -> range.first }.first
    val end = ranges.maxBy { range -> range.last }.last

    println("start: $start, end: $end")

    return mergeAndCount(ranges)
  }

  private fun mergeAndCount(ranges: List<LongRange>): Long {
    if (ranges.isEmpty()) return 0L

    val sorted = ranges.sortedBy { it.first }
    val merged = mutableListOf<LongRange>()
    var current = sorted[0]

    for (next in sorted.drop(1)) {
      if (next.first <= current.last + 1) {
        current = current.first..maxOf(current.last, next.last)
      } else {
        merged.add(current)
        current = next
      }
    }
    merged.add(current)

    return merged.sumOf { it.last - it.first + 1 }
  }

}

fun main() {
  Day5().start()
}