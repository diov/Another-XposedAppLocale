package io.github.diov.applocale

import android.content.pm.ApplicationInfo
import java.util.Locale

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-09-05.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

data class AppInfo(
    val name: String,
    val pkgName: String,
    val applicationInfo: ApplicationInfo,
    val localeString: String
) {
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + pkgName.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppInfo

        if (name != other.name) return false
        if (pkgName != other.pkgName) return false

        return true
    }

    fun modifyLocale(locale: Locale?): AppInfo {
        return AppInfo(
            name = name,
            pkgName = pkgName,
            applicationInfo = applicationInfo,
            localeString = locale?.toString() ?: ""
        )
    }
}
