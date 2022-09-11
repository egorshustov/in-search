package com.egorshustov.vpoiske.core.common.network

import android.webkit.JavascriptInterface
import com.egorshustov.vpoiske.core.common.utils.UrlString

class AppJsInterface {

    private var obtainPageCallback: ObtainPageCallback? = null

    @JavascriptInterface
    fun showHTML(url: UrlString, html: String) {
        obtainPageCallback?.onPageObtained(url, html)
    }

    interface ObtainPageCallback {
        fun onPageObtained(url: UrlString, html: String)
    }

    fun setObtainPageCallback(obtainPageCallback: ObtainPageCallback) {
        this.obtainPageCallback = obtainPageCallback
    }
}