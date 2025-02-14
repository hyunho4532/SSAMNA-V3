package com.asetec.presentation.enum

sealed class ButtonType {
    data object ROUTER: ButtonType()
    data object PERMISSION: ButtonType()

    sealed class RunningStatus: ButtonType() {
        data object FINISH : RunningStatus()
        data object OPEN: RunningStatus()
        sealed class InsertStatus: ButtonType() {
            data object RUNNING: InsertStatus()
            data object CHALLENGE: InsertStatus()
        }
    }

    sealed class HistoryStatus: ButtonType() {
        data object OPEN: RunningStatus()
    }

    sealed class MarkerStatus: ButtonType() {
        data object FINISH: MarkerStatus()
    }
}