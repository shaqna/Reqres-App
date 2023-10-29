package com.maou.reqresapp.presentation.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.maou.reqresapp.R
import com.maou.reqresapp.databinding.ActivitySettingsBinding
import com.maou.reqresapp.presentation.ui.auth.AuthActivity
import com.maou.reqresapp.presentation.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class SettingsActivity : AppCompatActivity() {

    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(SettingsViewModel.inject())

        setButtonAction()
    }

    private fun setButtonAction() {
        with(binding) {
            btnBack.setOnClickListener {
                back()
            }
            logoutLayout.setOnClickListener {
                showDialogLogout()
            }
        }
    }

    private fun back() {
        startActivity(Intent(this, HomeActivity::class.java)).also {
            finish()
        }
    }

    private fun showDialogLogout() {
        val materialBuilder = MaterialAlertDialogBuilder(this).create()
        val inflater: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_logout_layout, null)
        val btnAccept: Button = inflater.findViewById(R.id.btn_accept)
        val btnCancel: Button = inflater.findViewById(R.id.btn_cancel)

        btnAccept.setOnClickListener {
            materialBuilder.dismiss()
            viewModel.deleteToken()
            startActivity(
                Intent(
                    this,
                    AuthActivity::class.java
                )
            ).also {
                finishAffinity()
            }
        }

        btnCancel.setOnClickListener {
            materialBuilder.dismiss()
        }

        materialBuilder.setView(inflater)
        materialBuilder.show()
    }
}