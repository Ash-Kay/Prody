package com.ashkay.prody.ui.main

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashkay.prody.R
import com.ashkay.prody.adapters.AppListAdapter
import com.ashkay.prody.interfaces.OnLaunchAppListener
import com.ashkay.prody.models.App
import com.ashkay.prody.utils.ApplicationFetcher
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList


class AppListFragment : Fragment(), OnLaunchAppListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var rvAppListGrid: RecyclerView
    private lateinit var etSearchApp: TextInputEditText

    private lateinit var apps: List<App>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.app_list_fragment, container, false)
        initViews(rootView)
        return rootView
    }

    private fun initViews(rootView: View) {
        rvAppListGrid = rootView.findViewById(R.id.rvAppListGrid)
        etSearchApp = rootView.findViewById(R.id.etSearchApp)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        apps = ApplicationFetcher.getInstalledApplications(requireContext())
        rvAppListGrid.adapter = AppListAdapter(apps, this)
        rvAppListGrid.layoutManager = GridLayoutManager(context, 2)

        val usageStats = getUsageStatistics( UsageStatsManager.INTERVAL_DAILY)
        //TODO: FIX the data structure
        apps.forEach {app->
            usageStats?.forEach {
                if(it!=null && app.packageName == it.packageName){
                    app.minutesUsed = it.totalTimeInForeground/60000.0
                }
            }
        }
        val adapter = rvAppListGrid.adapter
        adapter?.notifyDataSetChanged()
        etSearchApp.addTextChangedListener(onTextChangeListener)
    }

    override fun onLaunch(app: App, view: View) {
        val manager = requireContext().getSystemService(Context.USER_SERVICE) as UserManager
        val launcher =
            requireContext().getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

        val componentName = ComponentName(app.packageName, app.activityName)
        val userHandle = manager.getUserForSerialNumber(app.userSerial)

        launcher.startMainActivity(componentName, userHandle, view.clipBounds, null)
    }

    private fun getUsageStatistics(intervalType: Int): List<UsageStats?>? {
        // Get the app statistics since one year ago from the current time.
        val cal: Calendar = Calendar.getInstance()
        val mUsageStatsManager = activity?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
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
            startActivity( Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
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
        etSearchApp.removeTextChangedListener(onTextChangeListener)
    }
}