package com.marinaruiz.facturas_fct.ui.auth

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.DynamicThemeActivity
import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.core.SecureSharedPrefs.removePasswordInSharedPrefs
import com.marinaruiz.facturas_fct.core.SecureSharedPrefs.retrieveFromSecSharedPrefs
import com.marinaruiz.facturas_fct.core.SecureSharedPrefs.saveInSecSharedPrefs
import com.marinaruiz.facturas_fct.core.extension.isValidEmail
import com.marinaruiz.facturas_fct.core.extension.toIsoDateFormat
import com.marinaruiz.facturas_fct.data.network.firebase.RemoteConfigService
import com.marinaruiz.facturas_fct.databinding.ActivityLoginBinding
import com.marinaruiz.facturas_fct.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.ZonedDateTime

@AndroidEntryPoint
class LoginActivity : DynamicThemeActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val remoteConfig = RemoteConfigService.getInstance()
    private val authVM: AuthViewModel by viewModels()
    private var padding: Int = 0
    private var email: String = ""
    private var password: String = ""

    companion object {
        const val TAG = "VIEWNEXT LoginActivity"

        fun create(context: Context): Intent {
            Log.d(TAG, "Creating LoginActivity")
            return Intent(context, LoginActivity::class.java)
        }
    }

    private fun initView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val theme = remoteConfig.getStringValue()
        setCurrentTheme(theme)
        enableEdgeToEdge()
        val view = binding.root
        setContentView(view)
        setWindowInsets()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        authVM.initRemoteConfig.observe(this) { withInternet ->
            if (withInternet != null && withInternet) {
                lifecycleScope.launch {
                    remoteConfig.loading.collect { loading ->
                        if (loading != null && !loading) {
                            remoteConfig.fetchAndActivate().addOnCompleteListener {
                                val theme = remoteConfig.getStringValue()
                                setCurrentTheme(theme)
                                recreate()
                            }
                        }
                    }
                }
                if (remoteConfig.loading.value == null) {
                    setTheme()
                    initView()
                    setVisibility()
                    initUI()
                }
            } else if (withInternet != null) {
                setVisibility()
                setWindowInsets()
                initUI()
            }
        }
    }

    private fun setVisibility() {
        with(binding) {
            loadingLogin.visibility = GONE
            ivLoadingLoginIberdrola.visibility = GONE
            ivLoginIberdrola.visibility = VISIBLE
            spaceLoginTop.visibility = VISIBLE
            etLoginUser.visibility = VISIBLE
            etLoginPassword.visibility = VISIBLE
            btnLoginEye.visibility = VISIBLE
            cbLoginRememberPassword.visibility = VISIBLE
            btnLoginForgotPassword.visibility = VISIBLE
            spaceLoginBottom.visibility = VISIBLE
            btnLoginAccept.visibility = VISIBLE
            dividerLoginLeft.visibility = VISIBLE
            tvLoginOtherOptionLabel.visibility = VISIBLE
            dividerLoginRight.visibility = VISIBLE
            btnLoginSignup.visibility = VISIBLE
            val image = getThemeImageDrawable(TAG)
            if (image != null) {
                ivThemeImageLogin.setImageResource(image)
                ivThemeImageLogin.visibility = VISIBLE
            }
        }
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
        checkAccess()
        initListeners()
        initObservables()
    }

    private fun checkAccess() {
        val isSessionExpired = authVM.isSessionExpired()
        val rememberPass = isPasswordRemembered()
        if (isSessionExpired) {
            showErrorDialog(
                ErrorResponse(
                    "Sesión expirada", "Por favor, vuelva a entrar con sus credenciales"
                )
            )
        }
        if (isSessionExpired || !rememberPass) {
            authVM.logout()
        }
    }

    private fun autocompleteEmail() {
        val saveCredentials = retrieveFromSecSharedPrefs("saveCredentials") == "true"
        val email = retrieveFromSecSharedPrefs("email")
        email?.let {
            binding.etLoginUser.setText(email)
        }
        binding.cbLoginRememberPassword.isChecked = saveCredentials
    }

    private fun initListeners() {
        with(binding) {
            val etEmail = etLoginUser
            val etPassword = etLoginPassword
            etEmail.doOnTextChanged { email, _, _, _ ->
                val valid = email.toString().isValidEmail()
                btnLoginAccept.isEnabled = valid && etPassword.text.isNotEmpty()
                if (!email.isNullOrEmpty() && !valid) {
                    etEmail.backgroundTintList = getColorStateList(R.color.md_theme_light_error)
                } else {
                    etEmail.backgroundTintList = getColorStateList(R.color.black)
                }
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                val notEmpty = etPassword.text.isNotEmpty()
                btnLoginAccept.isEnabled = notEmpty && etEmail.text.toString().isValidEmail()
            }
            btnLoginAccept.setOnClickListener {
                email = etEmail.text.toString()
                password = etPassword.text.toString()
                val saveCredentials = cbLoginRememberPassword.isChecked
                authVM.login(email, password)
                saveInSecSharedPrefs(
                    "loginTime", ZonedDateTime.now(ZoneOffset.UTC).toIsoDateFormat(locale = null)
                )
                saveInSecSharedPrefs("email", email)
                saveInSecSharedPrefs("saveCredentials", saveCredentials.toString())
                if (saveCredentials) {
                    saveInSecSharedPrefs("pass", password)
                } else {
                    removePasswordInSharedPrefs()
                }
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
        observeAllowAccess()
        authVM.showErrorDialog.observe(this) { showError ->
            showError?.let {
                showErrorDialog(showError)
            }
        }
    }

    private fun observeAllowAccess() {
        authVM.allowAccess.observe(this) { allow ->
            val rememberPass = isPasswordRemembered()
            if (allow && rememberPass || email.isNotEmpty() && password.isNotEmpty()) {
                navigateMain()
            }
        }
    }

    private fun isPasswordRemembered(): Boolean = retrieveFromSecSharedPrefs("pass") != null

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