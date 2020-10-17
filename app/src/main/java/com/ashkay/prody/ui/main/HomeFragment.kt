package com.ashkay.prody.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ashkay.prody.R
import com.ashkay.prody.utils.broadcastReceiver.BatteryWatcher

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvBattery: TextView
    private lateinit var batteryWatcher: BatteryWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
        initViews(rootView)
        initWatchers()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initViews(rootView: View) {
        tvBattery = rootView.findViewById(R.id.tvBattery)
    }

    private fun initWatchers() {
        batteryWatcher = BatteryWatcher(context, tvBattery)
        batteryWatcher.startWatch()
    }

    override fun onDestroy() {
        super.onDestroy()
        batteryWatcher.stopWatch()
    }
}