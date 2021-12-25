package com.egorshustov.vpoiske

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.egorshustov.vpoiske.ui.navigation.AppContent
import com.egorshustov.vpoiske.ui.theme.VPoiskeTheme

class VPoiskeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VPoiskeTheme { AppContent() }
        }
    }
}