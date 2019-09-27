package io.github.diov.applocale

import android.content.pm.ApplicationInfo
import java.util.Locale

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-08-02.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

val Locale.flagEmoji: String
    inline get() {
        val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
    }

val String.locale: Locale?
    inline get() {
        val localeParts = split("_", limit = 3)
        val language = localeParts[0]
        return if (language.isEmpty()) {
            null
        } else {
            val region = if (localeParts.count() >= 2) localeParts[1] else ""
            val variant = if (localeParts.count() >= 3) localeParts[2] else ""

            Locale(language, region, variant)
        }
    }

val ApplicationInfo.isSystem: Boolean
    inline get() {
        return flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
