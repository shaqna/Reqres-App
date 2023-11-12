package com.maou.reqresapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.maou.reqresapp.presentation.navigation.NavGraph
import com.maou.reqresapp.presentation.navigation.Screen
import com.maou.reqresapp.presentation.screens.auth.login.LoginScreen
import com.maou.reqresapp.ui.theme.ReqresAppTheme

class ReqresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReqresAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Main(
        modifier: Modifier = Modifier,
        navHostController: NavHostController = rememberNavController(),
        startDestination: String = Screen.Login.name
    ) {
        Scaffold(

        ) { innerPadding ->
            NavGraph(
                navHostController = navHostController,
                startDestination = startDestination,
                innerPadding = innerPadding
            )
        }
    }
}




