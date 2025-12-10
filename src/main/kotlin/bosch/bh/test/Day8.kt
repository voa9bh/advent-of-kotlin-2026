import java.io.File
import kotlin.math.sqrt

class Day8 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day8_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 40) { "expected 40 but got $result1" }

    val data2 = File("src/main/resources/day8_2.txt").readLines()
    val result2 = part1(data2,1000)
    println("result2 = $result2")
//
//    val result3 = part2(data1)
//    println("result3 = $result3")
//
//    check(result3 == 3) { "expected 3 but got $result3" }
//
//    val result4 = part2(data2)
//    println("result4 = $result4")
  }

  private fun part1(lines: List<String>, repetitions: Int = 10): Int {
    val coordinates = lines.map { line ->
      line.split(",").map { it.toInt() }
    }

    println("coordinates: $coordinates")

    var lastMin = 0.0

    val connections: MutableList<MutableList<List<Int>>> = mutableListOf()

    var foundConnections=0

    while(foundConnections<repetitions) {
      // find the smallest distance
      var minDistance = Double.MAX_VALUE
      var minCoordinates = Pair(listOf(0, 0, 0), listOf(0, 0, 0))

      for (i in 0 until coordinates.size) {
        for (j in 0 until coordinates.size) {
          if (i == j) continue

          val cord1=coordinates[i]
          val cord2=coordinates[j]

          val currentDistance = sqrt(
            (cord1[0] - cord2[0]).toDouble() * (cord1[0] - cord2[0]) +
                (cord1[1] - cord2[1]).toDouble() * (cord1[1] - cord2[1]) +
                (cord1[2] - cord2[2]).toDouble() * (cord1[2] - cord2[2])
          )

          if (currentDistance < minDistance && currentDistance > lastMin) {
            minDistance = currentDistance
            minCoordinates = Pair(cord1, cord2)
          }
        }
      }

      lastMin = minDistance

      // store the connection
      val foundList = connections.find { it.contains(minCoordinates.first) || it.contains(minCoordinates.second) }
      if (foundList != null) {
        var found=false
        if(!foundList.contains(minCoordinates.first)) {
          foundList.add(minCoordinates.first)
          found=true
        }
        if(!foundList.contains(minCoordinates.second)) {
          foundList.add(minCoordinates.second)
          found=true
        }
        if(found) {
          foundConnections++
        }
      } else {
        connections.add(mutableListOf(minCoordinates.first, minCoordinates.second))
        foundConnections++
      }

    }

    println("connections:")
    connections.forEach {
      println(it)
    }

    val top3Clusters = connections.sortedByDescending { it.size }.take(3)

    println("top3Clusters:")
    top3Clusters.forEach {
      println(it)
    }

    val result = top3Clusters.fold(1) { acc, list -> acc * list.size }

    return result
  }

  private fun part2(lines: List<String>): Int {
    return 5
  }

}

fun main() {
  Day8().start()
}