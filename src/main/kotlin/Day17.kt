import util.part1Result
import util.part2Result

interface HasNeighbors<T> {
    fun neighbors(): List<T>
}

data class Point3D(val x: Int, val y: Int, val z: Int) : HasNeighbors<Point3D> {
    override fun neighbors(): List<Point3D> {
        val result = mutableListOf<Point3D>()
        for (dx in -1..1) {
            for (dy in -1..1) {
                for (dz in -1..1) {
                    if (dx != 0 || dy != 0 || dz != 0) {
                        result.add(Point3D(x + dx, y + dy, z + dz))
                    }
                }
            }
        }
        return result
    }
}

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) : HasNeighbors<Point4D> {
    override fun neighbors(): List<Point4D> {
        val result = mutableListOf<Point4D>()
        for (dx in -1..1) {
            for (dy in -1..1) {
                for (dz in -1..1) {
                    for (dw in -1..1) {
                        if (dx != 0 || dy != 0 || dz != 0 || dw != 0) {
                            result.add(Point4D(x + dx, y + dy, z + dz, w + dw))
                        }
                    }
                }
            }
        }
        return result
    }
}

class ConwayCubes<T : HasNeighbors<T>>(initiallyActive: Collection<T>) {
    private val activeCubes = initiallyActive.toMutableSet()

    fun isActive(point: T): Boolean {
        return point in activeCubes
    }

    private fun flip(point: T) {
        if (point in activeCubes) {
            activeCubes.remove(point)
        } else {
            activeCubes.add(point)
        }
    }

    private fun allCubes(): Set<T> {
        return activeCubes + activeCubes.map { it.neighbors() }.flatten()
    }

    private fun cycle() {
        val pointsToFlip = mutableListOf<T>()
        for (point in allCubes()) {
            val activeNeighbors = point.neighbors().count { it in activeCubes }
            if (point in activeCubes) {
                if (activeNeighbors !in 2..3) {
                    pointsToFlip.add(point)
                }
            } else {
                if (activeNeighbors == 3) {
                    pointsToFlip.add(point)
                }
            }
        }
        for (point in pointsToFlip) {
            flip(point)
        }
    }

    fun runCycles(n: Int) {
        for (i in 1..n) {
            cycle()
        }
    }

    fun countActive(): Int {
        return activeCubes.size
    }
}

fun <T> String.map2dInputTo(create: (Int, Int) -> T): Set<T> {
    val lines = lines()
    val results = mutableSetOf<T>()
    for (x in lines.indices) {
        for (y in lines[x].indices) {
            if (lines[x][y] == '#') {
                results.add(create(x, y))
            }
        }
    }
    return results
}

fun main() {
    val initialState = """
        ######.#
        ##.###.#
        #.###.##
        ..#..###
        ##.#.#.#
        ##...##.
        #.#.##.#
        .###.###
    """.trimIndent()

    val cubes = ConwayCubes(initialState.map2dInputTo { x, y -> Point3D(x, y, z = 0) })
    cubes.runCycles(6)
    cubes.countActive().part1Result()

    val cubes4d = ConwayCubes(initialState.map2dInputTo { x, y -> Point4D(x, y, z = 0, w = 0) })
    cubes4d.runCycles(6)
    cubes4d.countActive().part2Result()
}