package com.abzal.tripsplit

import android.app.Application
import com.abzal.tripsplit.core.di.AppContainer

class TripSplitApp : Application() {
    val container: AppContainer by lazy { AppContainer() }
}
