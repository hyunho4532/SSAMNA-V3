package com.app.presentation.ui.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.domain.model.enum.ButtonType
import com.app.domain.model.user.User
import com.app.presentation.component.tool.CustomButton
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.presentation.viewmodel.StateViewModel

/**
 * 사용자 설정 화면
 */
@Composable
fun SettingScreen(
    user: User,
    stateViewModel: StateViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = user.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        CustomButton(
            type = ButtonType.EventStatus.DARKTHEME,
            width = setUpWidth(),
            height = 46.dp,
            text = "다크 모드 활성화",
            showIcon = true,
            backgroundColor = Color(0xFF5c9afa),
            shape = "Rectangle",
            themeViewModel = stateViewModel
        )
    }
}