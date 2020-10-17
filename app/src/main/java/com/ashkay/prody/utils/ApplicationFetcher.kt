package com.ashkay.prody.utils

import android.content.Context
import android.content.pm.LauncherApps
import android.os.Process
import android.os.UserManager
import com.ashkay.prody.models.App

class ApplicationFetcher {

    companion object {

        fun getInstalledApplications(context: Context) : List<App> {
            val list = mutableListOf<App>()

            val manager = context.getSystemService(Context.USER_SERVICE) as UserManager
            val launcher = context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
            val myUserHandle = Process.myUserHandle()

            for (profile in manager.userProfiles) {
                val prefix = if (profile == myUserHandle) "" else "\uD83C\uDD46 " //Unicode for boxed 'W'
                val profileSerial = manager.getSerialNumberForUser(profile)

                for (activityInfo in launcher.getActivityList(null, profile)) {
                    val app = App(
                        appName = prefix + activityInfo.label.toString(),
                        appIcon = activityInfo.getIcon(0),
                        packageName = activityInfo.applicationInfo.packageName,
                        activityName = activityInfo.name,
                        userSerial = profileSerial
                    )
                    list.add(app)
                }
            }

            list.sortBy{it.appName}
            return list
        }
    }
}