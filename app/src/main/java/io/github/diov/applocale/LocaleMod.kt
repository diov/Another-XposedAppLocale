package io.github.diov.applocale

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.diov.xppreferences.XpPreferences
import java.util.Locale

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-07-31.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class LocaleMod : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        hookAttachBaseContext()
    }

    private fun hookAttachBaseContext() {
        XposedHelpers.findAndHookMethod(
            ContextWrapper::class.java,
            "attachBaseContext",
            Context::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam?) {
                    super.beforeHookedMethod(param)

                    try {
                        val context = (param?.args?.get(0) as? Context) ?: return

                        val pkgName = context.packageName
                        val locale = getSpecificLocale(pkgName, context) ?: return
                        val config = Configuration(context.resources.configuration)

                        config.setLocale(locale)
                        val newContext = context.createConfigurationContext(config)

                        param.args[0] = newContext
                    } catch (e: Exception) {
                        XposedBridge.log(e)
                    }
                }
            })
    }

    private fun getSpecificLocale(pkgName: String, context: Context): Locale? {
        val prefs = XpPreferences.instance(context, PKG, NAME)
        val localeString = prefs.getString(pkgName, "")

        XposedBridge.log("$pkgName: $localeString")
        return localeString?.locale
    }

    companion object {
        const val PKG = BuildConfig.APPLICATION_ID
        const val NAME = "another_app_locale"
    }
}
