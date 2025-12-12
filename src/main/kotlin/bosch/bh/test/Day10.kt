import java.io.File

class Day10 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day10_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 7) { "expected 7 but got $result1" }

    val data2 = File("src/main/resources/day10_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 33) { "expected 33 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  data class Machine(
    val targetState: List<Boolean>,
    val buttons: List<List<Int>>,
    val joltageRequirements: List<Int>
  )

  fun parseLine(line: String): Machine {
    val targetState = line.substringAfter('[').substringBefore(']')
      .map { it == '#' }

    val buttonPattern = Regex("""\(([\d,]+)\)""")
    val buttons = buttonPattern.findAll(line).map { match ->
      match.groupValues[1].split(',').map { it.trim().toInt() }
    }.toList()

    val joltagePattern = Regex("""\{([\d,]+)\}""")
    val joltageRequirements = joltagePattern.find(line)?.groupValues?.get(1)
      ?.split(',')?.map { it.trim().toInt() } ?: emptyList()

    return Machine(targetState, buttons, joltageRequirements)
  }

  private fun part1(lines: List<String>): Int {
    val machines = lines.map { parseLine(it) }

    println(machines)

    return machines.sumOf { fewestPresses(it) }
  }

  private fun fewestPresses(machine: Machine): Int {
    val targetState = machine.targetState
    val buttons = machine.buttons
    val n = targetState.size

    // Start state: all false
    val startState = BooleanArray(n) { false }

    if (startState.toList() == targetState) return 0

    val queue = ArrayDeque<Pair<BooleanArray, Int>>()
    val visited = mutableSetOf<List<Boolean>>()

    queue.add(startState to 0)
    visited.add(startState.toList())

    while (queue.isNotEmpty()) {
      val (currentState, presses) = queue.removeFirst()

      // Try pressing each button
      for (button in buttons) {
        val newState = currentState.copyOf()

        // Toggle the indexed positions
        for (index in button) {
          newState[index] = !newState[index]
        }

        val newStateList = newState.toList()

        if (newStateList == targetState) {
          return presses + 1
        }

        if (newStateList !in visited) {
          visited.add(newStateList)
          queue.add(newState to presses + 1)
        }
      }
    }

    return -1 // Target state not reachable
  }


  private fun part2(lines: List<String>): Int {
    val machines = lines.map { parseLine(it) }
    return machines.sumOf {
      println ("processing $it")
      val fewestPresses = fewestPressesForJoltage(it)
      println("fewestPressesForJoltage: $fewestPresses")
      fewestPresses
    }
  }

  private fun fewestPressesForJoltage(machine: Machine): Int {
    val buttons = machine.buttons
    val target = machine.joltageRequirements
    val n = target.size
    val memo = mutableMapOf<List<Int>, Int>()

    fun search(current: IntArray, pressCount: Int): Int {
      val currentList = current.toList()

      if (currentList == target) return pressCount
      if (current.indices.any { current[it] > target[it] }) return Int.MAX_VALUE
      if (currentList in memo && memo[currentList]!! <= pressCount) return Int.MAX_VALUE

      memo[currentList] = pressCount
      var minResult = Int.MAX_VALUE

      for (button in buttons) {
        val newCurrent = current.copyOf()
        for (idx in button) {
          newCurrent[idx]++
        }
        val result = search(newCurrent, pressCount + 1)
        minResult = minOf(minResult, result)
      }

      return minResult
    }

    val result = search(IntArray(n), 0)
    return if (result == Int.MAX_VALUE) -1 else result
  }
}

fun main() {
  Day10().start()
}