package com.asetec.presentation.component.util

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.model.entry.KcalEntry
import com.asetec.domain.model.entry.KmEntry
import com.asetec.domain.model.entry.StepEntry
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
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
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
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfWeek..endOfWeek
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 저번 주 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getLastWeek(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {

    var sumList = 0.0

    val today = LocalDate.now()
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
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastWeek..endOfLastWeek
            }.sumOf { it.step }.toDouble()
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
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
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
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfMonth..endOfMonth
            }.sumOf { it.step }.toDouble()
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
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
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
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastMonth..endOfLastMonth
            }.sumOf { it.step }.toDouble()
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
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
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
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == todayYear
            }.sumOf { it.step }.toDouble()
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
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {
    var sumList = 0.0

    val lastYear = LocalDate.now().year - 1

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                Log.d("Calcul", entry.date)
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
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == lastYear
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 시간 변환 함수
 * 00:00 형식의 시간을 분 단위 또는 초 단위로 변환하는 함수
 */
fun convertTimeToSeconds(time: String): Int {
    val parts = time.split(":").map { it.toInt() }
    return parts[0] * 60 + parts[1]
}

/**
 * 페이즈 및 칼로리 계산
 */
fun analyzeRunningFeedback(time: String, distance: Double, calories: Double, onPaceReceive: (Double) -> Unit): String {
    val timeInMinutes = convertTimeToSeconds(time) / 60.0
    val pace = if (distance > 0) timeInMinutes / distance else 0.0

    onPaceReceive(pace)

    return when {
        pace < 5.0 -> "속도가 빠릅니다! 페이스 조절이 필요할 수 있어요."
        pace in 5.0..7.0 -> "적절한 페이스로 달리고 있습니다!"
        pace in 7.0 .. 10.0 -> "현재 페이스도 괜찮지만, 조금 더 달려주세요!"
        else -> "현재 페이스로는 부족합니다!\n계속 러닝을 하다보면 페이스가 높아질거에요!"
    }
}