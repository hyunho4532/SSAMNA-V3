package com.app.presentation.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.PratikFagadiya.smoothanimationbottombar.model.SmoothAnimationBottomBarScreens
import com.app.presentation.R
import com.app.presentation.animation.Screens
import com.app.presentation.component.bar.BottomNavigationBar
import com.app.presentation.route.ScreenNavigationConfiguration

@Composable
fun RootScreen() {

    val context = LocalContext.current

    val bottomNavigationItems = listOf(
        SmoothAnimationBottomBarScreens(
            Screens.HomeScreen.route,
            stringResource(id = R.string.nav_home),
            R.drawable.baseline_home_24
        ),
        SmoothAnimationBottomBarScreens(
            Screens.AnalyzeScreen.route,
            stringResource(id = R.string.nav_analyze),
            R.drawable.baseline_calendar_24
        ),
        SmoothAnimationBottomBarScreens(
            Screens.ProfileScreen.route,
            stringResource(id = R.string.nav_profile),
            R.drawable.baseline_person_24
        )
    )

    val navController = rememberNavController()
    val currentIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavigationItems,
                currentIndex = currentIndex,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            ScreenNavigationConfiguration(
                navController = navController,
                context = context
            )
        }
    }
}