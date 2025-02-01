package com.asetec.presentation.component.util

fun kmLoadProgress(goal: Float, updateProgress: (Float) -> Unit) {
    for (i in 1..goal.toInt()) {
        updateProgress(i.toFloat() / 100)
    }
}