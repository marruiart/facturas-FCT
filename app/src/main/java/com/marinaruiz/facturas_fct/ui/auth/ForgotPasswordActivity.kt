package com.marinaruiz.facturas_fct.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.extension.isValidEmail
import com.marinaruiz.facturas_fct.databinding.ActivityForgotPasswordBinding
import com.marinaruiz.facturas_fct.domain.ForgotPasswordUseCase
import javax.inject.Inject

class ForgotPasswordActivity @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private var padding: Int = 0

    companion object {
        private const val TAG = "VIEWNEXT ForgotPasswordActivity"

        fun create(context: Context): Intent = Intent(context, ForgotPasswordActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
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
    }

    private fun initListeners() {
        with(binding) {
            etForgotEmail.doOnTextChanged { email, _, _, _ ->
                val valid = email.toString().isValidEmail()
                btnForgotRemindPassword.isEnabled = valid
                if (!email.isNullOrEmpty() && !valid) {
                    etForgotEmail.backgroundTintList =
                        getColorStateList(R.color.md_theme_light_error)
                } else {
                    etForgotEmail.backgroundTintList = getColorStateList(R.color.black)
                }
            }

            btnForgotRemindPassword.setOnClickListener {
                val email = binding.etForgotEmail.text.toString()
                forgotPasswordUseCase(this@ForgotPasswordActivity, email)
            }
            btnForgotBackLogin.setOnClickListener { navigateLogin() }
        }
    }

    private fun navigateLogin() {
        startActivity(LoginActivity.create(this))
    }
}