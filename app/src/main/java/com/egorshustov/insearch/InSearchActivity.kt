package com.egorshustov.insearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.egorshustov.insearch.core.ui.theme.InSearchTheme
import com.egorshustov.insearch.ui.AppContent
import com.egorshustov.insearch.utils.LocalBackPressedDispatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InSearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides this.onBackPressedDispatcher
            ) {
                InSearchTheme {
                    AppContent()
                }
            }
        }
    }
}