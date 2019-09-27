package io.github.diov.applocale

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import io.github.diov.xppreferences.XpPreferences

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-08-05.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class LocaleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var app: Context

        val prefs: SharedPreferences by lazy { XpPreferences.instance(app, LocaleMod.PKG, LocaleMod.NAME) }
    }
}
