package com.asetec.presentation.enum

sealed class ButtonType {

    sealed class PermissionStatus: ButtonType() {
        data object POPUP: PermissionStatus()
        data object CLICK: PermissionStatus()
        data object CANCEL: PermissionStatus()
    }

    sealed class RunningStatus: ButtonType() {
        data object FINISH : RunningStatus()
        data object OPEN: RunningStatus()
        sealed class InsertStatus: ButtonType() {
            data object RUNNING: InsertStatus()
            data object CHALLENGE: InsertStatus()
        }
        sealed class DeleteStatus: ButtonType() {
            data object RUNNING: DeleteStatus()
            data object CHALLENGE: DeleteStatus()
        }
    }

    sealed class HistoryStatus: ButtonType() {
        data object OPEN: RunningStatus()
    }

    sealed class MarkerStatus: ButtonType() {
        data object FINISH: MarkerStatus()
    }

    sealed class CrewStatus: ButtonType() {
        data object INSERT: CrewStatus()
    }
}