import kotlin.Triple

fun List<Int>.getPairsSummingTo(sum: Int): List<Pair<Int, Int>> {
    val results = mutableListOf<Pair<Int, Int>>()
    for (i in this.indices) {
        for (j in i + 1 until size) {
            if (this[i] + this[j] == sum) {
                results.add(this[i] to this[j])
            }
        }
    }
    return results
}

fun List<Int>.getTriplesSummingTo(sum: Int): List<Triple<Int, Int, Int>> {
    val results = mutableListOf<Triple<Int, Int, Int>>()
    for (i in this.indices) {
        for (j in i + 1 until size) {
            for (k in j + 1 until size) {
                if (this[i] + this[j] + this[k] == sum) {
                    results.add(Triple(this[i], this[j], this[k]))
                }
            }
        }
    }
    return results
}

fun Triple<Int, Int, Int>.product() = first * second * third

fun main() {
    val input = Resources.getLines("day1.txt").map { it.toInt() }
    val pairs = input.getPairsSummingTo(2020)
    for (pair in pairs) {
        println("${pair.first} * ${pair.second} = ${pair.first * pair.second}")
    }

    val triples = input.getTriplesSummingTo(2020)
    for (triple in triples) {
        println("${triple.first} * ${triple.second} * ${triple.third} = ${triple.product()}")
    }
}