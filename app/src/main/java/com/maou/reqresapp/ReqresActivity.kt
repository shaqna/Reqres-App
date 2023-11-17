package com.maou.reqresapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.maou.reqresapp.presentation.navigation.nav_graph.SetupNavGraph
import com.maou.reqresapp.ui.theme.ReqresAppTheme

class ReqresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReqresAppTheme {
                val navHostController = rememberNavController()
                SetupNavGraph(
                    navHostController = navHostController
                )
            }
        }
    }


}




