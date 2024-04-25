package ch.jdc.math.bigdecimal

import java.math.BigDecimal


fun showCase(i: Int) {
    val s = "0.3"
    val d: Double = 0.3 // Even if the compiler can infer it as a double, it more for Documentation

    when (i) {
        1 -> {
            println(String.format("s = \"%s\"", s))
            println(String.format("s.toDouble => %s", s.toDouble()))
            println(String.format("s.toBigDecimal => %s", s.toBigDecimal()))

            println()
            println("---")
            println()
            return
        }

        2 -> {
            println(String.format("d = %s", d))
            println(String.format("d.toBigDecimal => %s", d.toBigDecimal()))
            return
        }

        3 -> {
            println("What happend if we convert double or string to BigDecimal using the constructor ?")
            val sbc = BigDecimal(s)
            val dbc = BigDecimal(d)

            println(String.format("BigDecimal(\"%s\") = %s", s, sbc))
            println(String.format("BigDecimal(%s) = %s", d, dbc))
            return
        }

        else -> println(String.format("Case %s is not valid or implemented yet. Try 1,2 or 3"))
    }
}
