package com.abzal.tripsplit.core.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.abzal.tripsplit.TripSplitApp

fun Context.appContainer(): AppContainer = (applicationContext as TripSplitApp).container

/**
 * Creates a ViewModel scoped to the current NavBackStackEntry.
 * [create] receives the [AppContainer] and the entry's [SavedStateHandle] (holds nav arguments).
 */
@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    crossinline create: (AppContainer, SavedStateHandle) -> VM,
): VM {
    val container = LocalContext.current.appContainer()
    return viewModel(
        factory = viewModelFactory {
            initializer { create(container, createSavedStateHandle()) }
        },
    )
}
