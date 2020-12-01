import org.junit.Assert.assertEquals
import org.junit.Test

class Day1Tests {
    @Test
    fun `test list sum pairs`() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val actual = numbers.getPairsSummingTo(10)
        val expected = listOf(
                1 to 9,
                2 to 8,
                3 to 7,
                4 to 6,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test list sum triples`() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val actual = numbers.getTriplesSummingTo(10)
        val expected = listOf(
                Triple(1, 2, 7),
                Triple(1, 3, 6),
                Triple(1, 4, 5),
                Triple(2, 3, 5),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test triple product function`() {
        val triple = Triple(2, 3, 4)
        val expected = 24
        val actual = triple.product()
        assertEquals(expected, actual)
    }
}