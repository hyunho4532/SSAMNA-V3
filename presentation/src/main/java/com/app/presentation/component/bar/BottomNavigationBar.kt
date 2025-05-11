package com.app.presentation.component.bar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.PratikFagadiya.smoothanimationbottombar.model.SmoothAnimationBottomBarScreens
import com.PratikFagadiya.smoothanimationbottombar.properties.BottomBarProperties
import com.PratikFagadiya.smoothanimationbottombar.ui.SmoothAnimationBottomBar
import com.app.presentation.viewmodel.StateViewModel

@Composable
fun BottomNavigationBar(
    items: List<SmoothAnimationBottomBarScreens>,
    currentIndex: MutableIntState,
    navController: NavHostController,
    stateViewModel: StateViewModel
) {
    SmoothAnimationBottomBar (
        navController = navController,
        bottomNavigationItems = items,
        initialIndex = currentIndex,
        bottomBarProperties = BottomBarProperties(
            backgroundColor = MaterialTheme.colorScheme.onSurface,
            indicatorColor = Color.White.copy(alpha = 0.5F),
            iconTintColor = Color.Gray,
            iconTintActiveColor = Color.Black,
            textActiveColor = Color.Black,
            cornerRadius = 18.dp,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),
        onSelectItem = {

        },
    )
}