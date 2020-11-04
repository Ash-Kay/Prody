package com.ashkay.prody.ui.appLauncher

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashkay.prody.databinding.ItemAppBinding
import com.ashkay.prody.models.App
import javax.inject.Inject

class AppListAdapter @Inject constructor() :
    RecyclerView.Adapter<AppListAdapter.AppListViewHolder>() {

    //Const
    val cf = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })

    var appList: List<App> = listOf()
    var onLaunchAppListener: OnLaunchAppListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListViewHolder {
        val binding = ItemAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppListViewHolder, position: Int) {
        holder.bind(appList[position])
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class AppListViewHolder(private val binding: ItemAppBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: App) {
            binding.tvAppName.text = item.appName
            binding.ivAppIcon.setImageDrawable(item.appIcon)
            binding.ivAppIcon.colorFilter = cf  //Adds Grayscale Effect
            binding.appContainer.setOnClickListener {
                onLaunchAppListener?.onLaunch(item, binding.root)
            }
            binding.tvAppUsage.text = item.appUsage?.totalTimeInForeground?.div(60000.0).toString()
        }
    }

    interface OnLaunchAppListener {
        fun onLaunch(app: App, view: View)
    }
}
