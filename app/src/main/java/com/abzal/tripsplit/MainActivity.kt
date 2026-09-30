package com.abzal.tripsplit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.navigation.AppNavHost
import com.abzal.tripsplit.core.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val container = (application as TripSplitApp).container
        val startDestination =
            if (container.authRepository.currentUser.value != null) Routes.HOME else Routes.SIGN_IN

        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                AppNavHost(startDestination = startDestination)
            }
        }
    }
}
