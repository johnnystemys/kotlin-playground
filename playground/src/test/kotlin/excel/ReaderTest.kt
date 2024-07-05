package excel

import ch.jdc.excel.ScheduleWithPeriodLine
import ch.jdc.excel.buildSheet
import ch.jdc.excel.defaultZoneId
import ch.jdc.excel.extractData
import java.time.OffsetDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ReaderTest {

    @Test
    fun `when build sheet should get the instance from poi`() {
        val sheet = ReaderTest::class.java.getResourceAsStream("/test.xls")?.let { buildSheet(it, "Schedule") }
        assertNotNull(sheet)
    }

    // TODO make this test pass ;)
    @Test
    fun `should extract data from sheet`() {
        val expectedData: MutableList<ScheduleWithPeriodLine> = mutableListOf()
        val startPeriodTime = OffsetDateTime.of(2001, 1, 1, 6, 35, 0, 0, defaultZoneId)
        val endPeriodTime = OffsetDateTime.of(2001, 1, 1, 17, 30, 0, 0, defaultZoneId)

        var c = 0L
        for (i in 2..9) {
            expectedData.add(ScheduleWithPeriodLine("A", startPeriodTime.plusDays(c), endPeriodTime.plusDays(c), i.toLong()))
            c++
        }


        val sheet = ReaderTest::class.java.getResourceAsStream("/test.xls")?.let { buildSheet(it, "Schedule") }
        val data = sheet?.let { extractData(it) }

        for (i in 0 until expectedData.size) {
            assertEquals(expectedData[i], data?.get(i))
        }
    }
}
