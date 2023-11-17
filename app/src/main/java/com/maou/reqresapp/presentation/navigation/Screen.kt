package com.maou.reqresapp.presentation.navigation


const val ROOT_GRAPH_ROUTE = "root"
const val AUTH_GRAPH_ROUTE = "auth"
const val HOME_GRAPH_ROUTE = "home"
sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object Login: Screen("login_screen")
    object Register: Screen("register_screen")
    object Home: Screen("home_screen")
    object Detail: Screen("detail_screen")
    object Settings: Screen("settings_screen")
    object Create: Screen("create_new_person_screen")
}