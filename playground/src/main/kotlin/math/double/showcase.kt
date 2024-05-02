package ch.jdc.math.double

fun showCase(i: Int) {
    println("> Show case: $i")
    when (i) {
        1 -> {
            val a: Double = 0.1
            val b: Double = 0.3
            val expected: Double = 0.4
            println(formatter(a, b, expected))
            return
        }
        2 -> {
            val a: Double = 0.1
            val b: Double = 0.2
            val expected: Double = 0.3
            println(formatter(a, b, expected))
            return
        }
        else -> println(String.format("Case %s is not valid or implemented yet. Try 1"))
    }
}

fun formatter(a: Double, b: Double, expected: Double): String {
    val result = a + b
    return "When add $a + $b we should get $expected\nActual $a + $b = $result\n"
}
