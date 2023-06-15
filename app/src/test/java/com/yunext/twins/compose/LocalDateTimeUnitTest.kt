package com.yunext.twins.compose

import com.yunext.twins.compose.ui.debug.data.sleepData
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

class LocalDateTimeUnitTest {
    private val line:()->Unit = {
        println("--------华丽分割线----------")
    }

    private fun pt(prefix:String="",block: () -> String) {
        println(prefix+block())
    }

    @Test
    fun c09(){
        line()
        pt("开始") {
            sleepData.earliestStartHour.toString()
        }

        pt("结束") {
            sleepData.latestEndHour.toString()
        }
    }

    @Test
    fun c08(){
        line()
        val system = Clock.system(ZoneOffset.ofHours(8))
        val systemUTC = Clock.systemUTC()
        pt {
            system.millis().toString()
        }
        pt {
            systemUTC.millis().toString()
        }
        pt {
            system.instant().toString()
        }
        pt {
            systemUTC.instant().toString()
        }
    }

    @Test
    fun c077(){
        val now = LocalDateTime.now()
        val birthday = LocalDateTime.of(1988,5,25,0,0,0,0)
        pt ("相差年："){
            ChronoUnit.YEARS.between(birthday,now).toString()
        }
        pt ("相差月："){
            ChronoUnit.MONTHS.between(birthday,now).toString()
        }
        pt ("相差周："){
            ChronoUnit.WEEKS.between(birthday,now).toString()
        }
        pt ("相差天："){
            ChronoUnit.DAYS.between(birthday,now).toString()
        }
        pt ("相差时："){
            ChronoUnit.HOURS.between(birthday,now).toString()
        }
        pt ("相差分："){
            ChronoUnit.MINUTES.between(birthday,now).toString()
        }
        pt ("相差秒："){
            ChronoUnit.SECONDS.between(birthday,now).toString()
        }
        pt ("相差微妙："){
            ChronoUnit.MILLIS.between(birthday,now).toString()
        }
        pt ("相差纳秒："){
            ChronoUnit.NANOS.between(birthday,now).toString()
        }
        pt ("相差半天："){
            ChronoUnit.HALF_DAYS.between(birthday,now).toString()
        }
        pt ("相差十年："){
            ChronoUnit.DECADES.between(birthday,now).toString()
        }
        pt ("相差世纪："){
            ChronoUnit.CENTURIES.between(birthday,now).toString()
        }
        pt ("相差千年："){
            ChronoUnit.MILLENNIA.between(birthday,now).toString()
        }
        pt ("相差纪元："){
            ChronoUnit.ERAS.between(birthday,now).toString()
        }
    }

    @Test
    fun c07(){
        val between = Period.between(LocalDate.of(1997,7,12), LocalDate.of(2023,6,8))
        pt {
            between.years.toString()
        }
        pt {
            between.months.toString()
        }
        pt {
            between.days.toString()
        }

    }

    @Test
    fun c06(){
        line()
        pt { "时刻：Instant" }
        pt ("秒"){
            Instant.now().epochSecond.toString()
        }
        pt ("hao秒"){
            Instant.now().toEpochMilli().toString()
        }
        pt ("好秒->时间"){
            Instant.ofEpochMilli(123).toString()
        }

        pt ("秒->时间"){
            Instant.ofEpochSecond(123).toString()
        }

    }

    @Test
    fun c05(){
        line()
        pt {
            LocalDate.now().toString()
        }

        pt {
            LocalDate.of(2023,1,2).toString()
        }

        pt {
            LocalTime.now().toString()
        }

        pt {
            LocalTime.of(23,58,57).toString()
        }
    }

    @Test
    fun c04(){
        line()
        val now = LocalDateTime.now()
        val yesterday = now.minusDays(1)
        pt {
            now.isAfter(yesterday).toString()
        }
        pt {
            now.isBefore(yesterday).toString()
        }
        pt {
            now.isEqual(yesterday).toString()
        }
    }

    @Test
    fun c03(){
        val now =  LocalDateTime.of(2023,2,28,23,33,51)
        pt {
            now.toString()
        }
        pt("秒-") {
            now.minusSeconds(1) .toString()
        }
        pt ("分+"){
            now.plusMinutes(1) .toString()
        }
        pt ("时+"){
            now.plusHours(1) .toString()
        }
        pt ("月+"){
            now.plusMonths(1) .toString()
        }
        pt ("日+"){
            now.plusDays(2) .toString()
        }
        pt ("年+"){
            now.plusYears(2) .toString()
        }

    }

    @Test
    fun c02(){
        val format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        pt {
            format
        }
        line()
        pt {
            LocalDateTime.parse(format, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString()
        }
        line()
        val date =  Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)))
        pt {
           date.toString()
        }
        pt {
            date.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDateTime().toString()
        }
    }

    @Test
    fun c01() {
        val localDateTime = LocalDateTime.now()

        line()
        println("localDateTime = $localDateTime")
        println("localDateTime = ${LocalDateTime.parse("$localDateTime")}")
        println("${localDateTime.year}")
        println("${localDateTime.month}")
        println("${localDateTime.monthValue}")
        println("${localDateTime.dayOfYear}")
        println("${localDateTime.dayOfMonth}")
        println("${localDateTime.dayOfWeek}")
        println("${localDateTime.dayOfWeek.value}")
        println("${localDateTime.hour}")
        println("${localDateTime.minute}")
        println("${localDateTime.second}")
        line()
        println("秒${Instant.now().epochSecond}")
        println("毫秒${Instant.now().toEpochMilli()}")
        line()
        pt {
            "秒->datetime" +
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(Instant.now().epochSecond),
                        ZoneOffset.ofHours(8)
                    )
        }

        pt {
            "毫秒->datetime" +
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(Instant.now().toEpochMilli()),
                        ZoneOffset.ofHours(8)
                    )
        }

    }
}