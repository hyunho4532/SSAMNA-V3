package com.asetec.presentation.enum

sealed class CardType {
    sealed class ActivateStatus: CardType() {
        data object Running: ActivateStatus()
        data object Activity: ActivateStatus()
    }
}

sealed class ProfileStatusType  {
    data object Activate: ProfileStatusType()
    data object Kcal: ProfileStatusType()
    data object Goal: ProfileStatusType()
}