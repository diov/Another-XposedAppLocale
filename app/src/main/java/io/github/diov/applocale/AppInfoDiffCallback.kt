package io.github.diov.applocale

import androidx.recyclerview.widget.DiffUtil

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-09-20.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class AppInfoDiffCallback(private val oldList: List<AppInfo>, private val newList: List<AppInfo>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.count()

    override fun getNewListSize(): Int = newList.count()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.pkgName == new.pkgName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.pkgName == new.pkgName
            && old.localeString == new.localeString
    }
}
