package com.asetec.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.asetec.domain.model.common.Code
import com.asetec.domain.usecase.common.CodeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonCodeViewModel @Inject constructor(
    private val codeCase: CodeCase
): ViewModel() {
    /**
     * 공통 코드 조회
     */
    suspend fun select(): List<Code> {
        return codeCase.select()
    }
}