import util.part1Result

data class Rule(
        val name: String,
        val firstRange: IntRange,
        val secondRange: IntRange,
) {

    fun isValueValid(value: Int): Boolean {
        return value in firstRange || value in secondRange
    }

    companion object {
        private val regex = Regex("^(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$")

        fun fromString(text: String): Rule {
            return regex.matchEntire(text)?.let { match ->
                val (name, firstLower, firstUpper, secondLower, secondUpper) = match.destructured
                val firstRange = firstLower.toInt()..firstUpper.toInt()
                val secondRange = secondLower.toInt()..secondUpper.toInt()
                Rule(name, firstRange, secondRange)
            } ?: throw IllegalArgumentException("Input string did not match the Regex")
        }
    }
}

data class ParsedInput(
        val rules: List<Rule>,
        val yourTicket: List<Int>,
        val otherTickets: List<List<Int>>
)

fun parseInput(input: String): ParsedInput {
    val (rules, yourTicket, otherTickets) = input.split(
            "\n\nyour ticket:\n",
            "\n\nnearby tickets:\n",
            limit = 3)
    val formattedRules = rules.split("\n").map { Rule.fromString(it) }
    val yourTicketList = yourTicket.split(",").map { it.toInt() }
    val otherTicketsList = otherTickets.split("\n").map { ticket -> ticket.split(",").map { it.toInt() } }
    return ParsedInput(formattedRules, yourTicketList, otherTicketsList)
}

fun calculateScanningErrorRate(rules: List<Rule>, tickets: List<List<Int>>): Int {
    var acc = 0
    for (field in tickets.flatten()) {
        val anyRuleIsValid = rules.map { rule -> rule.isValueValid(field) }.reduce { a, b -> a || b }
        if (!anyRuleIsValid) {
            acc += field
            continue
        }
    }

    return acc
}

fun main() {
    val input = parseInput(Resources.getText("day16.txt") ?: "")

    calculateScanningErrorRate(input.rules, input.otherTickets).part1Result()
}