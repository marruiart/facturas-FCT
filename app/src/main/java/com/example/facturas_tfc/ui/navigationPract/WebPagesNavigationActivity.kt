package com.example.facturas_tfc.ui.navigationPract

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.facturas.utils.WEB_PAGE_URL
import com.example.facturas_tfc.R
import com.example.facturas_tfc.databinding.ActivityWebPagesNavigationBinding
import com.example.facturas_tfc.ui.MainActivity
import com.google.android.material.appbar.MaterialToolbar

class WebPagesNavigationActivity : AppCompatActivity() {
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
        enableEdgeToEdge()
        binding = ActivityWebPagesNavigationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        toolbar = binding.navWebpageIncludeToolbar.mtoolbarBackButton
        initUI()
    }

    private fun setWindowInsets() {
        padding = resources.getDimension(com.example.facturas_tfc.R.dimen.dimen_size_16).toInt()

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
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se ha encontrado navegador", Toast.LENGTH_SHORT).show()
            openWebpageInWebView()
        }
    }

    private fun openWebpageInWebView() {
        val webView: WebView = binding.wvWebNav
        webView.loadUrl(WEB_PAGE_URL)
    }
}