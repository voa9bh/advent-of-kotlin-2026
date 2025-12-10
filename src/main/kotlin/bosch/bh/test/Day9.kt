import java.io.File
import kotlin.div
import kotlin.math.abs
import kotlin.ranges.rangeTo
import kotlin.text.compareTo
import kotlin.times

class Day9 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day9_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 50L) { "expected 50 but got $result1" }

    val data2 = File("src/main/resources/day9_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 24L) { "expected 24 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
      return "($x,$y)"
    }

    fun area(point: Point): Long {
      return (abs(x - point.x) + 1L) * (abs(y - point.y) + 1L)
    }
  }

  private fun part1(lines: List<String>): Long {
    val points=lines.map { line ->
      val splitLine = line.split(",")
      Point(splitLine[0].toInt(), splitLine[1].toInt())
    }

    val pointPairs = points.flatMapIndexed { i, p1 ->
      points.drop(i + 1).map { p2 -> Pair(p1, p2) }
    }

    val maxAreaPoints = pointPairs.maxBy { points ->
      points.first.area(points.second)
    }

    println("maxAreaPoints: $maxAreaPoints")

    return maxAreaPoints.first.area(maxAreaPoints.second)
  }

  private fun part2(lines: List<String>): Long {
    val points=lines.map { line ->
      val splitLine = line.split(",")
      Point(splitLine[0].toInt(), splitLine[1].toInt())
    }

    val pointPairs = points.flatMapIndexed { i, p1 ->
      points.drop(i + 1).map { p2 -> Pair(p1, p2) }
    }

    var count=0

    val maxAreaPoints = pointPairs.maxBy { pointsToCheck ->
      if(count%10==0) {
        println("Checked $count rectangles... of ${pointPairs.size}")
      }
      count++
      if(!isRectangleInside(pointsToCheck.first.x,pointsToCheck.first.y,pointsToCheck.second.x,pointsToCheck.second.y,points)){
        0L
      } else {
        pointsToCheck.first.area(pointsToCheck.second)
      }
    }

    println("maxAreaPoints: $maxAreaPoints")

    return maxAreaPoints.first.area(maxAreaPoints.second)
  }

  private fun isRectangleInside(x1: Int, y1: Int, x2: Int, y2: Int, polygon: List<Point>): Boolean {
    val minX = minOf(x1, x2)
    val maxX = maxOf(x1, x2)
    val minY = minOf(y1, y2)
    val maxY = maxOf(y1, y2)

    // Check corners first (fastest rejection)
    val corners = listOf(
      Point(minX, minY), Point(maxX, minY),
      Point(minX, maxY), Point(maxX, maxY)
    )
    if (!corners.all { isPointInsideOrOnBoundary(it, polygon) }) return false

    // Sample edge points instead of checking all
    val sampleSize = 5
    val xStep = maxOf(1, (maxX - minX) / sampleSize)
    val yStep = maxOf(1, (maxY - minY) / sampleSize)

    for (x in minX..maxX step xStep) {
      if (!isPointInsideOrOnBoundary(Point(x, minY), polygon)) return false
      if (!isPointInsideOrOnBoundary(Point(x, maxY), polygon)) return false
    }

    for (y in minY..maxY step yStep) {
      if (!isPointInsideOrOnBoundary(Point(minX, y), polygon)) return false
      if (!isPointInsideOrOnBoundary(Point(maxX, y), polygon)) return false
    }

    return true
  }

  private fun isPointInsideOrOnBoundary(point: Point, polygon: List<Point>): Boolean {
    // Check if point is a vertex
    if (point in polygon) return true

    // Check if point is on an edge
    for (i in polygon.indices) {
      val j = (i + 1) % polygon.size
      if (isPointOnSegment(point, polygon[i], polygon[j])) return true
    }

    // Use ray casting for interior check
    return isPointInPolygon(point, polygon)
  }

  private fun isPointInPolygon(point: Point, polygon: List<Point>): Boolean {
    var inside = false
    var j = polygon.size - 1

    for (i in polygon.indices) {
      if ((polygon[i].y > point.y) != (polygon[j].y > point.y) &&
        point.x < (polygon[j].x - polygon[i].x) * (point.y - polygon[i].y) /
        (polygon[j].y - polygon[i].y) + polygon[i].x) {
        inside = !inside
      }
      j = i
    }

    return inside
  }

  private fun isPointOnSegment(p: Point, a: Point, b: Point): Boolean {
    val minX = minOf(a.x, b.x)
    val maxX = maxOf(a.x, b.x)
    val minY = minOf(a.y, b.y)
    val maxY = maxOf(a.y, b.y)

    if (p.x !in minX..maxX || p.y !in minY..maxY) return false

    val crossProduct = (p.y - a.y) * (b.x - a.x) - (p.x - a.x) * (b.y - a.y)
    return crossProduct == 0
  }

}

fun main() {
  Day9().start()
}