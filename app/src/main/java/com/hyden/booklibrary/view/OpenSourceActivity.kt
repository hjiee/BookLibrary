package com.hyden.booklibrary.view

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import com.hyden.base.BaseActivity
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.model.OpenSourceModel
import com.hyden.booklibrary.databinding.ActivityOpensourceLicenseBinding
import com.hyden.booklibrary.databinding.RecyclerItemOpensourceLicenseBinding

class OpenSourceActivity : BaseActivity<ActivityOpensourceLicenseBinding>(R.layout.activity_opensource_license) {

    private val licenseInfo = mutableListOf<OpenSourceModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_retrofit),getString(R.string.content_license_retrofit)))
        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxjava),getString(R.string.content_license_rxjava)))
        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_glide),getString(R.string.content_license_glide)))

//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
//        licenseInfo.add(OpenSourceModel(getString(R.string.title_license_rxandroid),getString(R.string.content_license_rxandroid)))
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun initBind() {
        binding.apply {
            ivBack.setOnClickListener { finish() }
            rvOpensource.apply {
                adapter = object : BaseRecyclerView.SimpleAdapter<RecyclerItemOpensourceLicenseBinding,OpenSourceModel>(
                    layoutId = R.layout.recycler_item_opensource_license,
                    listItem = licenseInfo,
                    bindingVariableId = BR.item
                ) { }
            }
        }
    }

}