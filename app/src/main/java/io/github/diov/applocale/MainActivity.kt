package io.github.diov.applocale

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager

class MainActivity : AppCompatActivity() {

    private lateinit var appInfoContainer: ViewGroup
    private lateinit var appInfoProgress: ProgressBar
    private lateinit var appListView: RecyclerView
    private lateinit var appInfoModel: AppInfoModel
    private val adapter = AppInfoAdapter().apply {
        setAppInfoSelectedCallback(::showLanguageSheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        appInfoContainer = findViewById(R.id.appInfoContainer)
        appListView = findViewById(R.id.appListView)
        appInfoProgress = findViewById(R.id.appInfoProgress)

        appListView.layoutManager = LinearLayoutManager(this)
        appListView.adapter = adapter
    }

    private fun setupViewModel() {
        appInfoModel = ViewModelProvider(this).get(AppInfoModel::class.java)

        appInfoModel.getAppInfoList().observe(this, Observer { appInfos ->
            if (appInfos.isEmpty()) {
                updateProgressUi()
            } else {
                updateInfoList(appInfos)
            }
        })
    }

    private fun updateProgressUi() {
        TransitionManager.endTransitions(appInfoContainer)
        TransitionManager.beginDelayedTransition(appInfoContainer)
        appInfoProgress.isInvisible = false
        appListView.isInvisible = true
    }

    private fun updateInfoList(appInfos: List<AppInfo>) {
        adapter.update(appInfos)
        appInfoProgress.isInvisible = true
        appListView.isInvisible = false
    }

    private fun showLanguageSheet(appInfo: AppInfo) {
        val bottomSheet = LanguageBottomSheetFragment(appInfo)
        bottomSheet.setLanguageSelectedCallback {
            appInfoModel.updateAppInfo(appInfo, it)
            bottomSheet.dismissAllowingStateLoss()
        }
        bottomSheet.show(supportFragmentManager)
    }
}
