object Resources {
    fun getText(name: String): String? {
        return javaClass.classLoader.getResource(name)?.readText()
    }

    fun getLines(name: String): List<String> {
        return getText(name)?.lines() ?: listOf()
    }

    fun getInts(name: String): List<Int> {
        return getLines(name).map { it.toInt() }
    }
}