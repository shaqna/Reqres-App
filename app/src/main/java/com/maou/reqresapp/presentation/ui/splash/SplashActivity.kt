package com.maou.reqresapp.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.maou.reqresapp.R
import com.maou.reqresapp.presentation.ui.auth.AuthActivity
import com.maou.reqresapp.presentation.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadKoinModules(SplashViewModel.inject())

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            getToken()
            observeToken()
        }, 3000L)
    }

    private fun getToken() {
        viewModel.getToken()
    }

    private fun observeToken() {
        if(viewModel.token.value != null) {
            startActivity(Intent(this, HomeActivity::class.java)).also {
                finish()
            }
        } else {
            startActivity(Intent(this, AuthActivity::class.java)).also {
                finish()
            }
        }
    }

}