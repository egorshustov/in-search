package com.egorshustov.vpoiske.feature.auth

import android.net.http.SslError
import android.os.Build
import android.webkit.*
import androidx.annotation.RequiresApi
import com.egorshustov.vpoiske.feature.auth.utils.AuthWebViewErrorType

internal class AuthWebViewClient(
    private val jsInterfaceName: String,
    private val onErrorReceived: ((errorType: AuthWebViewErrorType) -> Unit)? = null
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        view?.loadUrl(
            "javascript:window.$jsInterfaceName.showHTML" +
                    "('$url', '<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');"
        )
        super.onPageFinished(view, url)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        onErrorReceived?.invoke(AuthWebViewErrorType.UNDEFINED)
        super.onReceivedSslError(view, handler, error)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        if (request?.isForMainFrame == true) {
            val errorType = if (error?.errorCode == ERROR_HOST_LOOKUP) {
                AuthWebViewErrorType.NO_INTERNET_CONNECTION_OR_UNAVAILABLE
            } else {
                AuthWebViewErrorType.UNDEFINED
            }
            onErrorReceived?.invoke(errorType)
        }
        super.onReceivedError(view, request, error)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        if (request?.isForMainFrame == true) onErrorReceived?.invoke(AuthWebViewErrorType.UNDEFINED)
        super.onReceivedHttpError(view, request, errorResponse)
    }
}