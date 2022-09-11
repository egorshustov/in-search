package com.egorshustov.vpoiske.core.common.utils

import android.webkit.WebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun WebView.loadUrlInMainThread(url: String) {
    CoroutineScope(Dispatchers.Main).launch { loadUrl(url) }
}

fun WebView.evaluateJavascriptInMainThread(script: String) {
    CoroutineScope(Dispatchers.Main).launch { evaluateJavascript(script, null) }
}