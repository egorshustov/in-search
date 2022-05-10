package com.egorshustov.vpoiske

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.search.api.SearchFeatureApi
import com.egorshustov.vpoiske.ui.AppContent
import com.egorshustov.vpoiske.ui.theme.VPoiskeTheme
import com.egorshustov.vpoiske.utils.LocalBackPressedDispatcher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VPoiskeActivity : ComponentActivity() {

    @Inject
    lateinit var searchFeatureApi: SearchFeatureApi

    @Inject
    lateinit var authFeatureApi: AuthFeatureApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides this.onBackPressedDispatcher
            ) {
                VPoiskeTheme {
                    AppContent(
                        searchFeatureApi = searchFeatureApi,
                        authFeatureApi = authFeatureApi
                    )
                }
            }
        }
    }
}