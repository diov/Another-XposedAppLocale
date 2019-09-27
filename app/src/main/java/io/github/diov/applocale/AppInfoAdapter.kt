package io.github.diov.applocale

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-09-05.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class AppInfoAdapter : RecyclerView.Adapter<AppInfoAdapter.ViewHolder>() {

    private val packageManager = LocaleApp.app.packageManager
    private var selectedCallback: AppInfoSelectedCallback? = null
    private var appInfoList = emptyList<AppInfo>()

    override fun getItemCount(): Int {
        return appInfoList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_app_info, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appInfo = appInfoList[position]
        holder.nameView.text = appInfo.name
        holder.packageView.text = appInfo.pkgName
        holder.iconView.setImageDrawable(packageManager.getApplicationIcon(appInfo.applicationInfo))
        holder.flagView.text = appInfo.localeString.locale?.flagEmoji
        holder.itemView.setOnClickListener {
            selectedCallback?.invoke(appInfo)
        }
    }

    fun setAppInfoSelectedCallback(callback: AppInfoSelectedCallback) {
        this.selectedCallback = callback
    }

    fun update(newList: List<AppInfo>) {
        val result = DiffUtil.calculateDiff(AppInfoDiffCallback(appInfoList, newList))
        result.dispatchUpdatesTo(this)
        appInfoList = newList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconView: ImageView = itemView.findViewById(R.id.appInfoIcon)
        val nameView: TextView = itemView.findViewById(R.id.appInfoName)
        val packageView: TextView = itemView.findViewById(R.id.appInfoPackage)
        val flagView: TextView = itemView.findViewById(R.id.appInfoFlag)
    }
}

typealias AppInfoSelectedCallback = (appInfo: AppInfo) -> Unit
