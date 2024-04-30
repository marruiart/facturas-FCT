package com.marinaruiz.facturas_fct.ui.auth

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.core.NetworkConnectionManager
import com.marinaruiz.facturas_fct.core.extension.isValidEmail
import com.marinaruiz.facturas_fct.core.extension.isValidPassword
import com.marinaruiz.facturas_fct.databinding.ActivityLoginBinding
import com.marinaruiz.facturas_fct.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authVM: AuthViewModel by viewModels()
    private val network = NetworkConnectionManager.getInstance(lifecycleScope)
    private var padding: Int = 0

    companion object {
        private const val TAG = "VIEWNEXT LoginActivity"

        fun create(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    init {
        network.startListenNetworkState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        initUI()
    }

    private fun setWindowInsets() {
        padding = resources.getDimension(R.dimen.dimen_size_16).toInt()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + padding,
                systemBars.top + padding,
                systemBars.right + padding,
                systemBars.bottom + padding
            )
            insets
        }
    }

    private fun initUI() {
        autocompleteEmail()
        initListeners()
        initObservables()
    }

    private fun autocompleteEmail() {
        val email = authVM.retrieveFromSecSharedPreferences(this, "email")
        if (email != null) {
            binding.etLoginUser.setText(email)
        }
    }

    private fun initListeners() {
        with(binding) {
            val etEmail = etLoginUser
            val etPassword = etLoginPassword
            etEmail.doOnTextChanged { text, _, _, _ ->
                val valid = text.toString().isValidEmail()
                Log.d(TAG, "EMAIL: ${valid}")
                btnLoginAccept.isEnabled = valid && etPassword.text.toString().isValidPassword()
                if (!text.isNullOrEmpty() && !valid) {
                    etEmail.backgroundTintList = getColorStateList(R.color.md_theme_light_error)
                } else {
                    etEmail.backgroundTintList = getColorStateList(R.color.black)
                }
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                val valid = text.toString().isValidPassword()
                Log.d(TAG, "PASSWORD: ${valid}")
                btnLoginAccept.isEnabled = valid && etEmail.text.toString().isValidEmail()
                if (!text.isNullOrEmpty() && !valid) {
                    etPassword.backgroundTintList = getColorStateList(R.color.md_theme_light_error)
                } else {
                    etPassword.backgroundTintList = getColorStateList(R.color.black)
                }
            }
            btnLoginAccept.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val saveCredentials = cbLoginRememberPassword.isChecked
                if (saveCredentials) {
                    authVM.saveInSecSharedPrefs(this@LoginActivity, "email", email)
                    authVM.saveInSecSharedPrefs(this@LoginActivity, "pass", password)
                }
                authVM.login(email, password)
            }
            btnLoginSignup.setOnClickListener { navigateSignup() }
            btnLoginForgotPassword.setOnClickListener { navigateForgotPassword() }
            btnLoginEye.setOnCheckedChangeListener { buttonView, isChecked ->
                authVM.showPassword(
                    buttonView as CheckBox, isChecked, etLoginPassword
                )
            }
        }

    }

    private fun initObservables() {

        authVM.allowAccess.observe(this) { allow ->
            if (allow) {
                navigateMain()
            }
        }
        authVM.showErrorDialog.observe(this) { showError ->
            showError?.let {
                showErrorDialog(showError)
            }
        }
    }

    private fun showErrorDialog(error: ErrorResponse) {
        MaterialAlertDialogBuilder(this).setTitle("Error: " + error.code).setMessage(error.message)
            .setNeutralButton("Aceptar") { dialog, which ->
                if (which == DialogInterface.BUTTON_NEUTRAL) dialog.dismiss()
            }.show()
    }

    private fun navigateMain() {
        startActivity(MainActivity.create(this))
        this.finish()
    }

    private fun navigateSignup() {
        startActivity(SignupActivity.create(this))
        this.finish()
    }

    private fun navigateForgotPassword() {
        startActivity(ForgotPasswordActivity.create(this))
    }
}