package com.hyden.base

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyden.util.NetworkStatus
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val loading = MutableLiveData<Boolean>()
    val loadingStatus = MutableLiveData<NetworkStatus>()

    init {
        loading.value = (false)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        hideLoading()
    }

    fun showLoading() {
        loading.value = (true)
    }

    fun hideLoading() {
        loading.value = (false)
    }
}