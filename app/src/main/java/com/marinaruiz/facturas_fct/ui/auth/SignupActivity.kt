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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.core.extension.comparePassword
import com.marinaruiz.facturas_fct.core.extension.isValidEmail
import com.marinaruiz.facturas_fct.core.extension.isValidPassword
import com.marinaruiz.facturas_fct.core.extension.validateEmailAndPassword
import com.marinaruiz.facturas_fct.databinding.ActivitySignupBinding
import com.marinaruiz.facturas_fct.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val authVM: AuthViewModel by viewModels()
    private var padding: Int = 0

    companion object {
        private const val TAG = "VIEWNEXT SignupActivity"
        private const val MIN_PASSWORD_LENGTH = 6
        fun create(context: Context): Intent = Intent(context, SignupActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        initUI()
    }

    private fun setWindowInsets() {
        padding = resources.getDimension(com.marinaruiz.facturas_fct.R.dimen.dimen_size_16).toInt()

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
        initListeners()
        initObservables()
    }

    private fun initListeners() {
        with(binding) {
            val etEmail = etSignupEmail
            val etPassword = etSignupPassword
            val etPasswordRepeat = etSignupPasswordRepeat
            etEmail.doOnTextChanged { text, _, _, _ ->
                val valid = text.toString().isValidEmail()
                Log.d(TAG, "EMAIL: ${valid}")
                btnSignupRegister.isEnabled = valid
                if (!text.isNullOrEmpty() && !valid) {
                    etEmail.backgroundTintList = getColorStateList(R.color.md_theme_light_error)
                } else {
                    etEmail.backgroundTintList = getColorStateList(R.color.black)
                }
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                val valid = text.toString().isValidPassword() && etPassword.text.toString()
                    .comparePassword(etPasswordRepeat.text.toString())
                Log.d(TAG, "PASSWORD: ${valid}")
                btnSignupRegister.isEnabled = valid && etEmail.text.toString().isValidEmail()
                if (!text.isNullOrEmpty() && !valid) {
                    etPassword.backgroundTintList = getColorStateList(R.color.md_theme_light_error)
                } else {
                    etPassword.backgroundTintList = getColorStateList(R.color.black)
                }
            }
            etPasswordRepeat.doOnTextChanged { text, _, _, _ ->
                val valid = text.toString().isValidPassword() && etPassword.text.toString()
                    .comparePassword(etPasswordRepeat.text.toString())
                Log.d(TAG, "PASSWORD: ${valid}")
                btnSignupRegister.isEnabled = valid && etEmail.text.toString().isValidEmail()
                if (!text.isNullOrEmpty() && !valid) {
                    etPasswordRepeat.backgroundTintList =
                        getColorStateList(R.color.md_theme_light_error)
                } else {
                    etPasswordRepeat.backgroundTintList = getColorStateList(R.color.black)
                }
            }
            btnSignupRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val passwordRepeat = etPasswordRepeat.text.toString()
                if (validateEmailAndPassword(email, password) && password.comparePassword(
                        passwordRepeat
                    )
                ) {
                    authVM.signUp(email, password)
                } else {
                    showErrorDialog(
                        ErrorResponse(
                            "Email or password not valid",
                            "Password must contain at least 6 characters"
                        )
                    )
                }
            }
            btnSignupBackLogin.setOnClickListener { navigateLogin() }
            btnSignupEye.setOnCheckedChangeListener { buttonView, isChecked ->
                authVM.showPassword(
                    buttonView as CheckBox, isChecked, etSignupPassword, etSignupPasswordRepeat
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

    private fun navigateLogin() {
        startActivity(LoginActivity.create(this))
        this.finish()
    }

}
