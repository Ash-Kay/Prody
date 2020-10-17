package com.ashkay.prody.interfaces

import android.view.View
import com.ashkay.prody.models.App

interface OnLaunchAppListener{
    fun onLaunch(app: App, view: View)
}