package com.example.percobaanp1.pertemuan_5

import android.os.Bundle
import android.view.Menu
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.percobaanp1.R
import com.example.percobaanp1.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Web Browser"
            setDisplayHomeAsUpEnabled(true)
        }

        // Listener Back di Toolbar (Panah Kiri)
        binding.toolbar.setNavigationOnClickListener {
            handleBackNavigation()
        }

        // 2. Setup WebView dengan Konfigurasi Lengkap
        setupWebView()

        // 3. Handle Tombol Back Fisik HP
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackNavigation()
            }
        })
    }

    private fun setupWebView() {
        binding.webView.apply {
            // Agar link terbuka di dalam aplikasi, bukan browser luar
            webViewClient = WebViewClient()

            // Memberikan dukungan untuk fitur browser modern (ProgressBar, Alert, dll)
            webChromeClient = WebChromeClient()

            settings.apply {
                javaScriptEnabled = true           // Wajib untuk web modern
                domStorageEnabled = true           // PENTING: Sering jadi penyebab ERR_ACCESS_DENIED jika OFF

                // Fitur Zoom
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false        // Sembunyikan tombol zoom yang mengganggu

                // Optimasi tampilan halaman
                useWideViewPort = true
                loadWithOverviewMode = true

                // Keamanan dan File
                allowContentAccess = true
                allowFileAccess = true

                // Mengatasi masalah HTTP di dalam situs HTTPS
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            loadUrl("https://www.merdeka.com")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.queryHint = "Cari atau masukkan URL..."
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    val targetUrl = when {
                        // Jika input diawali http, pakai langsung
                        query.startsWith("http://") || query.startsWith("https://") -> query

                        // Jika mengandung titik tapi tidak ada spasi (misal: wikipedia.org)
                        query.contains(".") && !query.contains(" ") -> "https://$query"

                        // Jika kata kunci biasa, lempar ke Google Search
                        else -> "https://www.google.com/search?q=$query"
                    }

                    binding.webView.loadUrl(targetUrl)
                    searchItem.collapseActionView()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = true
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun handleBackNavigation() {
        // Jika web bisa kembali ke halaman sebelumnya
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            // Jika sudah di halaman awal, tutup Activity
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        handleBackNavigation()
        return true
    }
}