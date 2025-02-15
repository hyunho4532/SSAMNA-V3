package com.asetec.presentation.ui.feature

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.asetec.presentation.R
import com.asetec.presentation.animation.SplashLoader
import com.asetec.presentation.component.dialog.PermissionDialog
import com.asetec.presentation.component.tool.CustomButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.component.util.responsive.setSubTitleFontSize
import com.asetec.presentation.component.util.responsive.setTitleFontSize
import com.asetec.presentation.component.util.responsive.setContentPadding

@Composable
fun OnBoardingScreen(
    navController: NavController
) {

    var isPermissionPopup = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        Log.d("OnBoardScreen", isPermissionPopup.toString())
    }

    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f
        )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            val screenWidth = maxWidth
            val screenHeight = maxHeight

            val titleFontSize = setTitleFontSize(screenWidth)
            val subtitleFontSize = setSubTitleFontSize(screenWidth)
            val contentPadding = setContentPadding(screenWidth)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.35f)
                ) {
                    SplashLoader(R.raw.init)
                }

                Text(
                    text = "운동할 땐 땀💦 배출하자!",
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = contentPadding)
                )

                Text(
                    text = "언제 어디서든 편하게!",
                    fontSize = subtitleFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = contentPadding)
                )

                Text(
                    text = "운동을 즐기세요",
                    fontSize = subtitleFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = contentPadding / 2)
                )

                Spacer(width = 0.dp, height = screenHeight * 0.2f)

                CustomButton(
                    type = ButtonType.PermissionStatus.POPUP,
                    width = screenWidth * 0.8f,
                    height = 46.dp,
                    text = "운동 여정하기!",
                    showIcon = true,
                    backgroundColor = Color(0xFF5c9afa),
                    shape = "Rectangle",
                    onClick = {
                        isPermissionPopup.value = it
                    }
                )
            }
        }

        if (isPermissionPopup.value) {
            PermissionDialog(
                isPermissionPopup = isPermissionPopup,
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
    }
}