package com.ashkay.prody.ui.appLauncher

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.os.Bundle
import android.os.UserManager
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ashkay.prody.R
import com.ashkay.prody.databinding.AppLauncherFragmentBinding
import com.ashkay.prody.models.App
import com.ashkay.prody.models.state.AppLauncherViewEffect
import com.ashkay.prody.models.state.AppLauncherViewEvent
import com.ashkay.prody.models.state.AppLauncherViewState
import com.ashkay.prody.models.state.AppListState
import com.ashkay.prody.ui.base.BaseFragment
import com.ashkay.prody.utils.ApplicationFetcher
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AppLauncherFragment :
    BaseFragment<AppLauncherViewState, AppLauncherViewEffect, AppLauncherViewEvent, AppLauncherViewModel>(
        R.layout.app_launcher_fragment
    ), AppListAdapter.OnLaunchAppListener {

    @Inject
    lateinit var appListAdapter: AppListAdapter

    override val viewModel: AppLauncherViewModel by viewModels()
    private lateinit var binding: AppLauncherFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AppLauncherFragmentBinding.bind(view)

        appListAdapter.onLaunchAppListener = this
        binding.rvAppListGrid.adapter = appListAdapter
        binding.rvAppListGrid.layoutManager = GridLayoutManager(context, 2)
        binding.etSearchApp.addTextChangedListener(onTextChangeListener)
    }

    override fun renderViewState(viewState: AppLauncherViewState) {
        when (viewState.apps) {
            AppListState.Loading -> loadAppList()
            is AppListState.Success -> TODO()
            is AppListState.Error -> TODO()
        }
    }

    override fun renderViewEffect(viewEffect: AppLauncherViewEffect) {
        when (viewEffect) {
            //todo
        }
    }

    private fun loadAppList() {
        val usageStats = getUsageStatistics(UsageStatsManager.INTERVAL_DAILY)
        val apps = ApplicationFetcher.getInstalledApplications(requireContext())

        usageStats?.forEach {
            if (it != null) {
                val app = apps[it.packageName]
                app?.appUsage?.totalTimeInForeground = it.totalTimeVisible
            }
        }

        appListAdapter.appList = apps.values.toList()
        appListAdapter.notifyDataSetChanged()
    }

    override fun onLaunch(app: App, view: View) {
        val manager = requireContext().getSystemService(Context.USER_SERVICE) as UserManager
        val launcher =
            requireContext().getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

        val componentName = ComponentName(app.packageName, app.activityName)
        val userHandle = manager.getUserForSerialNumber(app.userSerial)

        launcher.startMainActivity(componentName, userHandle, view.clipBounds, null)
    }

    private fun getUsageStatistics(intervalType: Int): List<UsageStats?> {
        // Get the app statistics since one year ago from the current time.
        val cal: Calendar = Calendar.getInstance()
        val mUsageStatsManager =
            activity?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        cal.add(Calendar.YEAR, -1)
        val queryUsageStats: List<UsageStats?> =
            mUsageStatsManager.queryUsageStats(
                intervalType, cal.timeInMillis,
                System.currentTimeMillis()
            )
        queryUsageStats.forEach {
            println("${it?.packageName} -> ${it?.totalTimeInForeground?.div(60000.0)}")
        }
        if (queryUsageStats.isEmpty()) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
            println("No permission maybe")
        }
        return queryUsageStats
    }

    private val onTextChangeListener: TextWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            // Do nothing
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//            viewModel.filterApps(s.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        binding.etSearchApp.removeTextChangedListener(onTextChangeListener)
    }
}