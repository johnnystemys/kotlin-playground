package math

import ch.jdc.math.Add
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AdderKtTest {

    @Test
    fun `when add two double should get correct result`() {
        assertEquals(0.3, Add(0.1, 0.2))
    }

    @Test
    fun `when add two BigDecimal should get correct result`() {
        assertEquals(BigDecimal(0.3), Add(BigDecimal(0.1), BigDecimal(0.2)))
    }

    @Test
    fun `when add two BigDecimal v2 should get correct result`() {
        assertEquals(BigDecimal("0.3"), Add(BigDecimal("0.1"), BigDecimal("0.2")))
    }
}
