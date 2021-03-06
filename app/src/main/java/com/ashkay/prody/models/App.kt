package com.ashkay.prody.models

import android.graphics.drawable.Drawable

data class App(
    val appName: String,
    val appIcon: Drawable,
    val packageName: String,
    val activityName: String,
    val userSerial: Long,
    var appUsage: AppUsage? = null
)

data class AppUsage(
    var launchCount: Int,
    var totalTimeInForeground: Long
)
