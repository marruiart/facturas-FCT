package com.marinaruiz.facturas_fct.ui.navigationPract

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.marinaruiz.facturas.utils.WEB_PAGE_URL
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.DynamicThemeActivity
import com.marinaruiz.facturas_fct.data.network.firebase.RemoteConfigService
import com.marinaruiz.facturas_fct.databinding.ActivityWebPagesNavigationBinding
import com.marinaruiz.facturas_fct.ui.MainActivity

class WebPagesNavigationActivity : DynamicThemeActivity() {
    private val remoteConfig = RemoteConfigService.getInstance()
    private lateinit var binding: ActivityWebPagesNavigationBinding
    private lateinit var toolbar: MaterialToolbar
    private var padding: Int = 0

    companion object {
        private const val TAG = "VIEWNEXT WebPagesNavigationActivity"

        fun create(context: Context): Intent =
            Intent(context, WebPagesNavigationActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = remoteConfig.getStringValue()
        setCurrentTheme(theme)
        enableEdgeToEdge()
        binding = ActivityWebPagesNavigationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        toolbar = binding.navWebpageIncludeToolbar.mtoolbarBackButton
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
        toolbar.setNavigationOnClickListener {
            navigateUpTo(MainActivity.create(this))
        }
        binding.btnOpenWebExternally.setOnClickListener {
            openWebpageExternally()
        }
        binding.btnOpenWebWebview.setOnClickListener {
            openWebpageInWebView()
        }
    }

    private fun openWebpageExternally() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(WEB_PAGE_URL))

        if (intent.resolveActivity(packageManager) != null) {
            binding.wvWebNav.visibility = View.GONE
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se ha encontrado navegador", Toast.LENGTH_SHORT).show()
            openWebpageInWebView()
        }
    }

    private fun openWebpageInWebView() {
        val webView: WebView = binding.wvWebNav
        webView.visibility = View.VISIBLE
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(WEB_PAGE_URL)
    }
}