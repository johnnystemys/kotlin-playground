package math

import ch.jdc.math.Add
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AdderKtTest {

    @Test
    fun `when add two double should get correct result`() {
        assertEquals(0.3, Add(0.1, 0.2))
    }

    @Test
    fun `when add two double parse from json should get correct result`() {
        @Serializable
        data class Payload(val a: Double, val b: Double)

        val data = """
            {
                "a": 0.1,
                "b": 0.2
            }
        """

        val values = Json.decodeFromString<Payload>(data)

        assertEquals(0.3, Add(values.a, values.b))
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
