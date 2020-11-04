package com.ashkay.prody.ui.appLauncher

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.ashkay.prody.models.state.AppLauncherViewEffect
import com.ashkay.prody.models.state.AppLauncherViewEvent
import com.ashkay.prody.models.state.AppLauncherViewState
import com.ashkay.prody.models.state.AppListState
import com.ashkay.prody.ui.base.BaseViewModel

class AppLauncherViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel<AppLauncherViewState, AppLauncherViewEffect, AppLauncherViewEvent>(application) {

    init {
        viewState = AppLauncherViewState(AppListState.Loading)
    }

    override fun process(viewEvent: AppLauncherViewEvent) {
        super.process(viewEvent)
        println()
    }
}