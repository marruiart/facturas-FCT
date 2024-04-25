package com.marinaruiz.facturas_fct.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.NetworkConnectionManager
import com.marinaruiz.facturas_fct.databinding.ActivityLoginBinding
import com.marinaruiz.facturas_fct.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            btnLoginAccept.setOnClickListener { navigateMain() }
            btnLoginSignup.setOnClickListener { navigateSignup() }
            btnLoginForgotPassword.setOnClickListener { navigateForgotPassword() }
        }

    }

    private fun navigateMain() {
        startActivity(MainActivity.create(this))
        this.finish()
    }

    private fun navigateSignup() {
        startActivity(SignupActivity.create(this))
    }

    private fun navigateForgotPassword() {
        startActivity(ForgotPasswordActivity.create(this))
    }
}