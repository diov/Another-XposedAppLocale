package io.github.diov.applocale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_language_sheet.*
import java.util.Locale

/**
 * AnotherAppLocale
 *
 * Created by Dio_V on 2019-09-20.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class LanguageBottomSheetFragment(private val appInfo: AppInfo) : BottomSheetDialogFragment() {

    private var languageSelectedCallback: LanguageSelectedCallback? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_language_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        languageAppIcon.setImageDrawable(LocaleApp.app.packageManager.getApplicationIcon(appInfo.applicationInfo))
        languageAppTitle.text = appInfo.name

        val defaultIndex = languageList.indexOfFirst { locale ->
            locale.toString() == appInfo.localeString
        }
        languageGroupView.forEachIndexed { index, view ->
            val chip = view as Chip
            val locale = languageList[index]
            val title = "${locale.flagEmoji} ${locale.toLanguageTag()}"
            chip.text = title
            chip.isChecked = index == defaultIndex
        }
        languageGroupView.setOnCheckedChangeListener { _, checkedId ->
            val language = when (checkedId) {
                R.id.frenchChip -> languageList[0]
                R.id.germanChip -> languageList[1]
                R.id.italianChip -> languageList[2]
                R.id.japaneseChip -> languageList[3]
                R.id.koreanChip -> languageList[4]
                R.id.simplifiedChineseChip -> languageList[5]
                R.id.traditionalChineseChip -> languageList[6]
                R.id.englishChip -> languageList[7]
                else -> null
            }
            languageSelectedCallback?.invoke(language)
        }
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

    fun setLanguageSelectedCallback(callback: LanguageSelectedCallback) {
        this.languageSelectedCallback = callback
    }

    companion object {
        private const val TAG = "io.github.diov.applocale.LanguageBottomSheetFragment"

        private val languageList =
            listOf(
                Locale.FRANCE,
                Locale.GERMANY,
                Locale.ITALY,
                Locale.JAPAN,
                Locale.KOREA,
                Locale.PRC,
                Locale.TAIWAN,
                Locale.UK
            )
    }
}

typealias LanguageSelectedCallback = (language: Locale?) -> Unit
