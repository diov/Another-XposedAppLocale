package io.github.diov.applocale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.framg_language_sheet.*
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
        return inflater.inflate(R.layout.framg_language_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        languageAppIcon.setImageDrawable(LocaleApp.app.packageManager.getApplicationIcon(appInfo.applicationInfo))
        languageAppTitle.text = appInfo.name

        languageListView.layoutManager = LinearLayoutManager(requireContext())
        languageListView.adapter = LanguageAdapter()
        languageResetButton.setOnClickListener {
            languageSelectedCallback?.invoke(null)
        }
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

    fun setLanguageSelectedCallback(callback: LanguageSelectedCallback) {
        this.languageSelectedCallback = callback
    }

    private inner class LanguageAdapter : RecyclerView.Adapter<LanguageViewHolder>() {
        override fun getItemCount(): Int = languageList.count()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
            return LanguageViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
            val locale = languageList[position]
            val title = "${locale.flagEmoji} ${locale.toLanguageTag()}"
            (holder.itemView as TextView).text = title
            holder.itemView.setOnClickListener {
                languageSelectedCallback?.invoke(locale)
            }
        }
    }

    private inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
