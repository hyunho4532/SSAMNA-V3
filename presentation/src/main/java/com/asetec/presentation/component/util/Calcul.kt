package com.asetec.presentation.component.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.asetec.domain.model.state.ActivateDTO
import com.asetec.domain.model.state.KcalEntry
import com.asetec.domain.model.state.KmEntry
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * 비례 관계를 이용하여 card의 width를 조정하는 함수
 */
fun calculatorActivateCardWeight(activateData: State<List<ActivateDTO>>): Dp {

    val size = activateData.value.size

    return if (size > 0) {
        /**
         * 비례 관계
         */
        (320 * (size / 2f)).coerceIn(160f, 320f).dp
    } else {
        0.dp
    }
}

/**
 * 이번 주 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getThisWeek(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList()
): Double {

    var sumList = 0.0

    val today = LocalDate.now()

    val startOfWeek = today.with(DayOfWeek.MONDAY)
    val endOfWeek = today.with(DayOfWeek.SUNDAY)

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfWeek..endOfWeek
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfWeek..endOfWeek
            }.sumOf { it.km }
        }
    }

    return sumList
}

/**
 * 저번 주 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
fun getLastWeek(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList()
): Double {

    var sumList = 0.0

    val today = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val startOfLastWeek = today.with(DayOfWeek.MONDAY).minusDays(7)
    val endOfLastWeek = today.with(DayOfWeek.SUNDAY).minusDays(7)

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastWeek..endOfLastWeek
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastWeek..endOfLastWeek
            }.sumOf { it.km }
        }
    }

    return sumList
}

/**
 * 이번 달 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getThisMonth(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList()
): Double {
    var sumList = 0.0

    val today = LocalDate.now()

    val startOfMonth = today.withDayOfMonth(1)
    val endOfMonth = today.withDayOfMonth(today.lengthOfMonth())

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfMonth..endOfMonth
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfMonth..endOfMonth
            }.sumOf { it.km }
        }
    }

    return sumList
}

/**
 * 저번 달 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getLastMonth(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList()
): Double {
    var sumList = 0.0

    val today = LocalDate.now()
    val lastMonth = today.minusMonths(-1)

    val startOfLastMonth = lastMonth.withDayOfMonth(1)
    val endOfLastMonth = lastMonth.withDayOfMonth(today.lengthOfMonth())

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastMonth..endOfLastMonth
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastMonth..endOfLastMonth
            }.sumOf { it.km }
        }
    }

    return sumList
}

/**
 * 올해 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getThisYear(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList()
): Double {
    var sumList = 0.0

    val todayYear = LocalDate.now().year

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == todayYear
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == todayYear
            }.sumOf { it.km }
        }
    }

    return sumList
}

/**
 * 작년 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getLastYear(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList()
): Double {
    var sumList = 0.0

    val lastYear = LocalDate.now().year - 1

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == lastYear
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == lastYear
            }.sumOf { it.km }
        }
    }

    return sumList
}