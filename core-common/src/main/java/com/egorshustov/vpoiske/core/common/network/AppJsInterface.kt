package com.egorshustov.vpoiske.core.common.network

import android.webkit.JavascriptInterface

class AppJsInterface {

    private var obtainPageCallback: ObtainPageCallback? = null

    @JavascriptInterface
    fun showHTML(url: String, html: String) {
        obtainPageCallback?.onPageObtained(url, html)
    }

    interface ObtainPageCallback {
        fun onPageObtained(url: String, html: String)
    }

    fun setObtainPageCallback(obtainPageCallback: ObtainPageCallback) {
        this.obtainPageCallback = obtainPageCallback
    }
}