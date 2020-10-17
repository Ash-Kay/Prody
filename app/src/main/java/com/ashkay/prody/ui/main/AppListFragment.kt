package com.ashkay.prody.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.pm.LauncherApps
import android.os.Bundle
import android.os.UserManager
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

class AppListFragment : Fragment(), OnLaunchAppListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var rvAppListGrid: RecyclerView

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val apps = ApplicationFetcher.getInstalledApplications(requireContext())
        rvAppListGrid.adapter = AppListAdapter(apps, this)
        rvAppListGrid.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onLaunch(app: App, view: View) {
        val manager = requireContext().getSystemService(Context.USER_SERVICE) as UserManager
        val launcher =
            requireContext().getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

        val componentName = ComponentName(app.packageName, app.activityName)
        val userHandle = manager.getUserForSerialNumber(app.userSerial)

        launcher.startMainActivity(componentName, userHandle, view.clipBounds, null)
    }
}