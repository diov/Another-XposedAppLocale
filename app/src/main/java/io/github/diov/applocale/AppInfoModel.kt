package io.github.diov.applocale

import android.content.pm.PackageManager
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-09-05.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class AppInfoModel : ViewModel() {

    enum class Type {
        Default,
        IncludeSystem,
        OnlyModified
    }

    private val packageManager = LocaleApp.app.packageManager
    private val prefs = LocaleApp.prefs
    private val appInfoList = MutableLiveData<List<AppInfo>>()
    private var appInfos: Set<AppInfo> = emptySet()
    private var type = Type.Default
        set(value) {
            if (value == field) return
            postInfos(value)
            field = value
        }

    init {
        viewModelScope.launch {
            appInfos = getAppInfos()
            postInfos(type)
        }
    }

    private fun postInfos(type: Type) {
        val infos = when (type) {
            Type.Default -> {
                appInfos.filter { !it.applicationInfo.isSystem }
            }
            Type.IncludeSystem -> {
                appInfos
            }
            Type.OnlyModified -> {
                appInfos.filter { it.localeString.isNotEmpty() }
            }
        }.sortedBy { it.name }
        appInfoList.postValue(infos)
    }

    fun updateType(type: Type) {
        this.type = type
    }

    private fun getAppInfos(): MutableSet<AppInfo> {
        val applications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return applications
            .map {
                AppInfo(
                    name = packageManager.getApplicationLabel(it).toString(),
                    pkgName = it.packageName,
                    applicationInfo = it,
                    localeString = prefs.getString(it.packageName, "")!!
                )
            }
            .toMutableSet()
    }

    fun getAppInfoList(): LiveData<List<AppInfo>> {
        return appInfoList
    }

    fun updateAppInfo(appInfo: AppInfo, locale: Locale?) {
        val newList = appInfos.toMutableSet()
        val removed = newList.remove(appInfo)
        if (removed) {
            newList.add(appInfo.modifyLocale(locale))
            locale?.run {
                prefs.edit { putString(appInfo.pkgName, locale.toString()) }
            } ?: run {
                prefs.edit { remove(appInfo.pkgName) }
            }
            appInfos = newList
            postInfos(type)
        }
    }
}
