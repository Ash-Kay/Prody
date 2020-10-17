package com.ashkay.prody.utils.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.widget.TextView

class BatteryWatcher(private val context: Context?, private val tvBatteryLevel: TextView) {

    private val receiver = BatteryBroadcastReceiver()
    private val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

    fun startWatch() {
        context?.registerReceiver(receiver, filter)
    }

    fun stopWatch() {
        context?.unregisterReceiver(receiver)
    }

    inner class BatteryBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            tvBatteryLevel.text = "$level%"
        }
    }
}