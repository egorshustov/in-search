package com.egorshustov.vpoiske.feature.auth

import android.net.http.SslError
import android.os.Build
import android.webkit.*
import androidx.annotation.RequiresApi
import com.egorshustov.vpoiske.feature.auth.utils.AuthErrorType

internal class AuthWebViewClient(
    private val jsInterfaceName: String,
    private val onErrorReceived: ((authErrorType: AuthErrorType) -> Unit)? = null
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        view?.loadUrl(
            "javascript:window.$jsInterfaceName.showHTML" +
                    "('$url', '<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');"
        )
        super.onPageFinished(view, url)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        onErrorReceived?.invoke(AuthErrorType.UNDEFINED)
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
                AuthErrorType.NO_INTERNET_CONNECTION_OR_UNAVAILABLE
            } else {
                AuthErrorType.UNDEFINED
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
        if (request?.isForMainFrame == true) onErrorReceived?.invoke(AuthErrorType.UNDEFINED)
        super.onReceivedHttpError(view, request, errorResponse)
    }
}