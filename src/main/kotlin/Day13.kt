sealed class Bus {
    data class InService(val id: Int) : Bus()
    object OutOfService : Bus()
}

fun parseInputAssumingOutOfServiceBuses(input: String): Pair<Int, List<Bus>> {
    val (line1, line2) = input.split('\n', limit = 2)
    val timeStamp = line1.toInt()
    val buses = parseBuses(line2)
    return timeStamp to buses
}

fun parseBuses(input: String) = input.split(',').map {
    when (val id = it.toIntOrNull()) {
        null -> Bus.OutOfService
        else -> Bus.InService(id)
    }
}

fun findEarliestBus(timestamp: Int, buses: List<Bus>): Bus.InService {
    return buses.filterIsInstance<Bus.InService>()
            .minByOrNull { it.id - timestamp % it.id }
            ?: throw IllegalArgumentException("No valid buses")
}

fun findEarliestBusTimeWait(timestamp: Int, buses: List<Bus>): Int {
    val bus = findEarliestBus(timestamp, buses)
    return bus.id * (bus.id - timestamp % bus.id)
}

data class IndexedBus(val index: Int, val busId: Int)

fun indexInServiceBuses(buses: List<Bus>): List<IndexedBus> {
    val result = mutableListOf<IndexedBus>()
    for ((index, bus) in buses.withIndex()) {
        if (bus is Bus.InService) {
            result.add(IndexedBus(index, bus.id))
        }
    }
    return result
}

fun findTimeStampForBuses(buses: List<Bus>): Int {
    val indexedBuses = indexInServiceBuses(buses)
    val product = indexedBuses.fold(1L) { acc, i -> acc * i.busId }
    for (bus in indexedBuses) {
        println("${bus.index}: ${bus.busId}\t${product/bus.busId}")
    }

    return 0
}

fun main() {
    val input = Resources.getText("day13.txt") ?: return
    val (timestamp, buses) = parseInputAssumingOutOfServiceBuses(input)

    println("Part 1 Result: ${findEarliestBusTimeWait(timestamp, buses)}")
    println("Part 2 Result: ${findTimeStampForBuses(buses)}")
}