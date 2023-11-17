package com.maou.reqresapp.presentation.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.maou.reqresapp.presentation.navigation.AUTH_GRAPH_ROUTE
import com.maou.reqresapp.presentation.navigation.HOME_GRAPH_ROUTE
import com.maou.reqresapp.presentation.navigation.ROOT_GRAPH_ROUTE

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = AUTH_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        authGraph(navHostController, context)
        homeGraph(navHostController, context)
    }
}



