package com.asetec.presentation.route

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asetec.domain.model.location.Coordinate
import com.asetec.domain.model.user.User
import com.asetec.presentation.animation.Screens
import com.asetec.presentation.ui.feature.login.LoginScreen
import com.asetec.presentation.ui.feature.login.ReportScreen
import com.asetec.presentation.ui.feature.login.UserInfoScreen
import com.asetec.presentation.ui.main.home.screen.HomeScreen
import com.asetec.presentation.ui.main.home.screen.ProfileScreen
import com.asetec.presentation.ui.feature.OnBoardingScreen
import com.asetec.presentation.ui.feature.detail.DetailScreen
import com.asetec.presentation.ui.feature.detail.chart.ActivateChart
import com.asetec.presentation.ui.main.home.screen.CalendarScreen
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.JsonParseViewModel
import com.asetec.presentation.viewmodel.UserViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.serialization.json.Json

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            OnBoardingScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable(
            route = "userInfo?authState={authState}",
            arguments = listOf(navArgument("authState") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val authStateJson = backStackEntry.arguments?.getString("authState")
            val user = Json.decodeFromString<User>(authStateJson!!)

            UserInfoScreen(
                navController = navController,
                user = user,
                context = context
            )
        }

        composable(
            route = "report?userState={userState}",
            arguments = listOf(
                navArgument("userState") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val userStateJson = backStackEntry.arguments?.getString("userState")
            val userState = Json.decodeFromString<User>(userStateJson!!)

            ReportScreen(userState)
        }
    }
}

@Composable
fun ScreenNavigationConfiguration(
    navController: NavHostController,
    context: Context,
    userViewModel: UserViewModel = hiltViewModel(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {

    val isClickable = remember {
        mutableStateOf(true)
    }

    val userList = userViewModel.user.collectAsState()
    val activateList = activityLocationViewModel.activateData.collectAsState()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(key1 = Unit) {
        val googleId = userViewModel.getSavedLoginState()
        userViewModel.selectUserFindById(googleId)
        activityLocationViewModel.selectActivityFindByGoogleId(googleId)
    }

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {

        composable(Screens.HomeScreen.route) {
            HomeScreen(
                fusedLocationClient = fusedLocationClient,
                context = context
            )
        }

        if (isClickable.value) {
            composable(Screens.AnalyzeScreen.route) {
                CalendarScreen(
                    activateList = activateList,
                    userList = userList
                )
            }
        }

        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                context = context,
                userList = userList
            )
        }

        composable(
            route = "activateDetail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            DetailScreen(
                id = id!!,
                navController = navController
            )
        }

        composable(
            route = "activateChart?coords={coords}",
            arguments = listOf(navArgument("coords") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val coords = backStackEntry.arguments?.getString("coords")
            val coordsList: List<Coordinate> = jsonParseViewModel.dataFromJson(coords!!)

            ActivateChart(
                coordsList = coordsList
            )
        }
    }
}