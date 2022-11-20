package com.egorshustov.insearch.feature.auth.utils

import com.egorshustov.insearch.core.common.network.DEFAULT_API_VERSION

internal object AuthHelper {

    private const val CLIENT_ID = 6604827
    private const val CLIENT_SECRET = "KfFi8pmxRpGDLdmlqruf"
    private const val REQUIRED_TOKEN_SCOPE = "messages,offline"
    private const val REDIRECT_URL = "https://oauth.vk.com/blank.html"

    const val URL_FOR_OBTAIN_TOKEN =
        "https://oauth.vk.com/authorize?client_id=$CLIENT_ID" +
                "&scope=$REQUIRED_TOKEN_SCOPE" +
                "&redirect_uri=$REDIRECT_URL" +
                "&display=mobile" +
                "&v=$DEFAULT_API_VERSION" +
                "&response_type=token&revoke=1"

    const val URL_FOR_OBTAIN_CODE =
        "https://oauth.vk.com/authorize?client_id=$CLIENT_ID" +
                "&scope=$REQUIRED_TOKEN_SCOPE" +
                "&redirect_uri=$REDIRECT_URL" +
                "&display=mobile" +
                "&v=$DEFAULT_API_VERSION" +
                "&response_type=code&revoke=1"

    fun getUrlForObtainTokenByCode(code: String): String =
        "https://oauth.vk.com/access_token?client_id=$CLIENT_ID" +
                "&scope=$REQUIRED_TOKEN_SCOPE" +
                "&client_secret=$CLIENT_SECRET" +
                "&redirect_uri=$REDIRECT_URL" +
                "&display=mobile" +
                "&code=$code"

    fun getFillCredentialsAndSubmitScript(login: String, password: String): String =
        "javascript:document.getElementsByName('email')[0].value='$login';" +
                "document.getElementsByName('pass')[0].value='$password';" +
                "document.querySelector('input.button').click()"

    const val CLICK_BUTTON_SCRIPT = "javascript:document.querySelector('.button').click()"

    const val SERVICE_MSG_WARNING_HTML_TEXT = "service_msg_warning"
    const val EMAIL_INPUT_HTML_CODE = "name=\"email\""
    const val SECURITY_ERROR_HTML_TEXT = "Security Error"
    const val AUTHORIZATION_CODE_ERROR_HTML_TEXT = "Code is invalid or expired"
    const val SUBMIT_BUTTON_HTML_CODE = "class=\"button\" type=\"submit\""
}