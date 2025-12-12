package bosch.bh.test

import java.io.File

class Day11 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day11_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 5) { "expected 5 but got $result1" }

    val data2 = File("src/main/resources/day11_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val data3 = File("src/main/resources/day11_1_2.txt").readLines()

    val result3 = part2(data3)
    println("result3 = $result3")

    check(result3 == 2) { "expected 2 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun parseDevices(lines: List<String>): Map<String, List<String>> {
    return lines.associate { line ->
      val (device, rest) = line.split(":")
      device.trim() to rest.trim().split(" ").filter { it.isNotEmpty() }
    }
  }

  private fun findAllPaths(
    graph: Map<String, List<String>>,
    start: String,
    end: String
  ): List<List<String>> {
    val allPaths = mutableListOf<List<String>>()
    val queue = ArrayDeque<Pair<String, List<String>>>()
    queue.add(start to listOf(start))

    while (queue.isNotEmpty()) {
      val (current, path) = queue.removeFirst()

      if (current == end) {
        allPaths.add(path)
        continue
      }

      val neighbors = graph[current] ?: continue
      for (next in neighbors) {
        if (next !in path) {
          queue.add(next to path + next)
        }
      }
    }

    return allPaths
  }

  private fun part1(lines: List<String>): Int {
    val graph = parseDevices(lines)
    val allPaths = findAllPaths(graph, "you", "out")
    // Print paths if needed: allPaths.forEach { println(it) }
    return allPaths.size
  }

  private fun part2(lines: List<String>): Int {
    val graph = parseDevices(lines)
    val allPaths = findAllPaths(graph, "svr", "out")
    allPaths.forEach { println(it) }
    val result = allPaths.count { path ->
      path.contains("dac") && path.contains("fft")
    }
    return result
  }

}

fun main() {
  Day11().start()
}