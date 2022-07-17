package com.example.yandexwebview

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient(private val context: Context) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val url = request?.url.toString()
        with(url) {
            when {
                contains(WEATHER_URL) -> openApp(context, packageNameWeather)
                contains(MAPS_URL) -> openApp(context, packageNameMaps)
            }
        }
        return super.shouldOverrideUrlLoading(view, request)

    }

    override fun onPageFinished(view: WebView?, url: String?) {
        val currentUrl = view?.url
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(PREF_KEY, currentUrl).apply()
        super.onPageFinished(view, url)
    }

    private fun openApp(context: Context, packageName: String) {
        val intentApp = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intentApp != null) {
            context.startActivity(intentApp)
        }
    }

    companion object {

        const val SHARED_PREF = "SHARED_PREF"
        const val PREF_KEY = "URL"

        const val DEFAULT_URL = "https://yandex.ru"
        private const val WEATHER_URL = "/pogoda"
        private const val MAPS_URL = "/maps"

        private const val packageNameWeather = "ru.yandex.weatherplugin"
        private const val packageNameMaps = "ru.yandex.yandexmaps"
    }
}