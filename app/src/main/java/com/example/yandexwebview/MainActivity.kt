package com.example.yandexwebview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.yandexwebview.CustomWebViewClient.Companion.DEFAULT_URL
import com.example.yandexwebview.CustomWebViewClient.Companion.PREF_KEY
import com.example.yandexwebview.CustomWebViewClient.Companion.SHARED_PREF


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled", "QueryPermissionsNeeded")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val savedUrl = sharedPreferences.getString(PREF_KEY, null)

        webView = findViewById(R.id.web_view)
        webView.webViewClient = CustomWebViewClient(this)
        webView.webChromeClient
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        if (savedUrl == null) {
            webView.loadUrl(DEFAULT_URL)
        } else {
            webView.loadUrl(savedUrl)
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Вы собираетесь выйти из приложения?")
            .setPositiveButton("Да") { _, _ -> super.onBackPressed() }
            .setNegativeButton("Нет") { dialog, _ -> dialog.cancel() }
            .setCancelable(true)
            .show()
    }

}



