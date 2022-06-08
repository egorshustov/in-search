package com.egorshustov.vpoiske.feature.auth.login_auth

import android.annotation.SuppressLint
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import com.egorshustov.vpoiske.core.common.network.AppJsInterface
import com.egorshustov.vpoiske.core.common.utils.UrlString
import com.egorshustov.vpoiske.core.common.utils.evaluateJavascriptInMainThread
import com.egorshustov.vpoiske.core.common.utils.loadUrlInMainThread
import com.egorshustov.vpoiske.core.common.utils.prepareUrlAndParseSafely
import com.egorshustov.vpoiske.feature.auth.utils.AuthErrorType
import com.egorshustov.vpoiske.feature.auth.utils.AuthHelper
import com.egorshustov.vpoiske.feature.auth.utils.AuthRequestType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
@Composable
internal fun AuthProcessWebView(
    login: String,
    password: String,
    onAuthDataObtained: (userId: String, accessToken: String) -> Unit,
    onError: (authErrorType: AuthErrorType) -> Unit
) {
    var webView: WebView
    val appJsInterface = remember { AppJsInterface() }
    val authWebViewClient = remember {
        AuthWebViewClient(
            jsInterfaceName = appJsInterface::javaClass.name,
            onErrorReceived = onError
        )
    }
    var currentRequestType = remember { AuthRequestType.IMPLICIT_FLOW }

    AndroidView(factory = {
        webView = WebView(it).apply {
            settings.javaScriptEnabled = true
            webViewClient = authWebViewClient
            visibility = View.GONE
        }
        CoroutineScope(Dispatchers.Main).launch {
            webView.clearCache(true) // TODO remove after testing
        }
        CookieManager.getInstance().removeAllCookies {
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        }
        appJsInterface.setObtainPageCallback(object : AppJsInterface.ObtainPageCallback {

            override fun onPageObtained(url: UrlString, html: String) {
                val uri = url.prepareUrlAndParseSafely() ?: return
                when (currentRequestType) {
                    AuthRequestType.IMPLICIT_FLOW -> {
                        when {
                            html.contains(AuthHelper.SERVICE_MSG_WARNING_HTML_TEXT) -> {
                                onError(AuthErrorType.WRONG_LOGIN_OR_PASSWORD)
                                return
                            }
                            html.contains(AuthHelper.EMAIL_INPUT_HTML_CODE) -> {
                                webView.evaluateJavascriptInMainThread(
                                    AuthHelper.getFillCredentialsAndSubmitScript(login, password)
                                )
                                return
                            }
                            html.contains(AuthHelper.SECURITY_ERROR_HTML_TEXT) -> {
                                currentRequestType = AuthRequestType.AUTHORIZATION_CODE_FLOW
                                webView.loadUrlInMainThread(AuthHelper.URL_FOR_OBTAIN_CODE)
                                return
                            }
                            else -> {
                                val accessToken = uri.getQueryParameter("access_token")
                                val userId = uri.getQueryParameter("user_id")
                                if (userId != null && accessToken != null) {
                                    onAuthDataObtained(userId, accessToken)
                                    CoroutineScope(Dispatchers.Main).launch {
                                        webView.clearCache(true) // TODO remove after testing
                                    }
                                    return
                                }
                            }
                        }
                    }
                    AuthRequestType.AUTHORIZATION_CODE_FLOW -> {
                        when {
                            html.contains(AuthHelper.AUTHORIZATION_CODE_ERROR_HTML_TEXT) -> {
                                onError(AuthErrorType.UNDEFINED)
                            }
                            else -> {
                                uri.getQueryParameter("code")?.let { code ->
                                    webView.loadUrlInMainThread(
                                        AuthHelper.getUrlForObtainTokenByCode(code)
                                    )
                                }
                            }
                        }
                        return
                    }
                }
                if (html.contains(AuthHelper.SUBMIT_BUTTON_HTML_CODE)) {
                    webView.loadUrlInMainThread(AuthHelper.CLICK_BUTTON_SCRIPT)
                }
            }
        })
        webView.apply { addJavascriptInterface(appJsInterface, appJsInterface::javaClass.name) }
    }, update = {
        it.loadUrl(AuthHelper.URL_FOR_OBTAIN_TOKEN)
    })
}