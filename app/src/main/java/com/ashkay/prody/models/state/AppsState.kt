package com.ashkay.prody.models.state

import com.ashkay.prody.models.App

data class AppLauncherViewState(val apps: AppListState)

sealed class AppLauncherViewEvent {
    object LaunchApp : AppLauncherViewEvent()
}

sealed class AppLauncherViewEffect {
}

sealed class AppListState {
    object Loading : AppListState()
    data class Success(val apps: List<App>) : AppListState()
    data class Error(val message: String) : AppListState()
}