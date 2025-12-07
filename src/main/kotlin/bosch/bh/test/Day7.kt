import java.io.File

class Day7 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day7_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 21L) { "expected 21 but got $result1" }

    val data2 = File("src/main/resources/day7_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 40L) { "expected 40 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Long {
    val charList = lines.map { line -> line.map { ch -> ch } as MutableList } as MutableList

    var splitCount = 0L

    for (y in 1..<charList.size) {
      for (x in charList[y].indices) {
        if (charList[y - 1][x] == 'S') {
          charList[y][x] = '|'
        } else if (charList[y - 1][x] == '|') {
          if (charList[y][x] == '^') {
            if (charList[y][x - 1] != '|') {
              charList[y][x - 1] = '|'
            }
            if (charList[y][x + 1] != '|') {
              charList[y][x + 1] = '|'
            }
            splitCount++
          } else {
            charList[y][x] = '|'
          }
        }
      }
    }

    charList.forEach {
      println(it.joinToString(""))
      println()
    }

    return splitCount
  }

  private fun part2(lines: List<String>): Long {
    // Convert lines to a static grid of characters (no need for MutableList of MutableList<Char>)
    val charGrid: List<List<Char>> = lines.map { it.toList() }

    // Initialize the PathCountGrid (PathCountGrid[y][x] = number of paths arriving at (y,x))
    // Use Long for large numbers
    val pathCountGrid: MutableList<MutableList<Long>> =
      MutableList(charGrid.size) { MutableList(charGrid[0].size) { 0L } }

    val height = charGrid.size
    val width = charGrid[0].size

    // 1. Initialize Start: Find 'S' and set its path count to 1
    var startX = -1
    for ((x, char) in charGrid[0].withIndex()) {
      if (char == 'S') {
        startX = x
        pathCountGrid[0][startX] = 1L // Start cell has 1 path
        break
      }
    }

    // 2. Propagate Paths Down (Dynamic Programming)
    for (y in 1 until height) {
      for (x in 0 until width) {

        // The path count coming from the cell directly above (y-1, x)
        val pathsFromAbove = pathCountGrid[y - 1][x]
        if (pathsFromAbove == 0L) continue // No paths reached the cell above, so skip

        // Check the character at the SOURCE cell: (y-1, x)
        when (charGrid[y - 1][x]) {
          '|', '.', 'S' -> {
            // Case 1: Straight Path
            // All paths continue straight down to (y, x)
            pathCountGrid[y][x] += pathsFromAbove
          }
          '^' -> {
            // Case 2: Split Path
            // All paths split left to (y, x-1) and right to (y, x+1)

            // Split Left (check bounds)
            if (x > 0) {
              pathCountGrid[y][x - 1] += pathsFromAbove
            }

            // Split Right (check bounds)
            if (x < width - 1) {
              pathCountGrid[y][x + 1] += pathsFromAbove
            }
          }
        }
      }
    }

    // Optional: Print the path counts for debugging
    pathCountGrid.forEach {
      it.forEach { print(" $it ".padStart(4)) }
      println()
    }

    // 3. The final answer is the sum of paths in the last row
    return pathCountGrid.last().sum()
  }

}

fun main() {
  Day7().start()
}