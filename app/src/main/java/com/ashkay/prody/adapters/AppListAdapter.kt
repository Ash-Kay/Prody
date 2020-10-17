package com.ashkay.prody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ashkay.prody.R
import com.ashkay.prody.interfaces.OnLaunchAppListener
import com.ashkay.prody.models.App

class AppListAdapter(appList: List<App>, private val listener: OnLaunchAppListener) :
    RecyclerView.Adapter<AppListAdapter.AppListAdapterViewHolder>() {

    private val appsList: List<App> = appList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return AppListAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppListAdapterViewHolder, position: Int) {
        holder.bind(appsList[position])
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    inner class AppListAdapterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private var tvAppName: TextView = view.findViewById(R.id.tvAppName)
        private var ivAppIcon: ImageView = view.findViewById(R.id.ivAppIcon)
        private var appContainer: View = view.findViewById(R.id.appContainer)

        fun bind(item: App) {
            tvAppName.text = item.appName
            ivAppIcon.setImageDrawable(item.appIcon)
            appContainer.setOnClickListener {
                listener.onLaunch(item, view)
            }
        }
    }
}
