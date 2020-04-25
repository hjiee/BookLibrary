package com.hyden.booklibrary.data.repository

import android.app.Application
import android.content.Context
import com.android.billingclient.api.*

class BillingRepository(private val applicationContext : Context) {

    private val billingClient by lazy {
        BillingClient.newBuilder(applicationContext)
            .enablePendingPurchases()
            .setListener(purchasesListener)
            .build()
    }

    private val purchasesListener = PurchasesUpdatedListener { p0, p1 ->

    }

    private val billingstateListener = object : BillingClientStateListener {
        override fun onBillingServiceDisconnected() {

        }

        override fun onBillingSetupFinished(p0: BillingResult?) {

        }
    }

    fun donation() {
        billingClient.startConnection(billingstateListener)
    }

}