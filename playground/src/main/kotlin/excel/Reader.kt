package ch.jdc.excel

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.InputStream
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException


val defaultZoneId: ZoneOffset = ZoneOffset.UTC

data class ImportExportHeader(
    val name: String,
    val index: Int,
    val required: Boolean,
    val unique: Boolean
)

class ScheduleHeaderSheet {
    companion object {
        val HEADER_START_DATE = ImportExportHeader("Start date", 0, true, false)
        val HEADER_END_DATE = ImportExportHeader("End date", 1, true, false)
        val HEADER_NAME = ImportExportHeader("Name", 2, true, false)
    }
}

data class ScheduleWithPeriodLine(
    val name: String? = null,
    val startDate: OffsetDateTime? = null,
    val endDate: OffsetDateTime? = null,
    val rowNum: Long
)

fun buildSheet(s: InputStream, sheetName: String): Sheet? {
    return try {
        val workbook: Workbook = HSSFWorkbook(s)
        workbook.getSheet(sheetName)
    } catch (e: Exception) {
        null
    }
}

fun extractData(sheet: Sheet): List<ScheduleWithPeriodLine> {
    val data = mutableListOf<ScheduleWithPeriodLine>()
    for (i in 1..sheet.lastRowNum) {
        val row = sheet.getRow(i)
        if (isEmptyRow(row)) {
            continue
        }
        val line = extractLine(row, (i + 1).toLong())
        data.add(line)
    }
    return data
}

fun isEmptyRow(row: Row): Boolean {
    for (cellNum in row.firstCellNum ..  row.lastCellNum) {
        val cell = row.getCell(cellNum)
        if (cell != null && cell.toString().isNotBlank()) {
            return false
        }
    }
    return true
}

private fun extractLine(row: Row, rowNum: Long): ScheduleWithPeriodLine {
    return ScheduleWithPeriodLine(
        getCellStringValue(row.getCell(ScheduleHeaderSheet.HEADER_NAME.index)),
        toOffsetDatetime(getCellLocalDatetimeValue(row.getCell(ScheduleHeaderSheet.HEADER_START_DATE.index))),
        toOffsetDatetime(getCellLocalDatetimeValue(row.getCell(ScheduleHeaderSheet.HEADER_END_DATE.index))),
        rowNum
    )
}

fun getCellStringValue(cell: Cell?): String? {
    return try {
        cell?.stringCellValue
    } catch (e: Exception) {
        null
    }
}

fun getCellLocalDatetimeValue(cell: Cell?): LocalDateTime? {
    return try {
        cell?.localDateTimeCellValue
    } catch (e: Exception) {
        null
    }
}

private fun toOffsetDatetime(localDateTime: LocalDateTime?): OffsetDateTime? {
    return try {
        localDateTime?.let { roundToNearestSecond(it) }?.atOffset(defaultZoneId)
    } catch (e: DateTimeParseException) {
        null
    }
}

private fun roundToNearestSecond(dateTime: LocalDateTime): LocalDateTime {
    val nano = dateTime.nano
    if (nano >= 500_000_000) {
        return dateTime.withNano(0).plusSeconds(1)
    }
    return dateTime.withNano(0)
}
