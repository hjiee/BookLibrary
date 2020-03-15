package com.hyden.booklibrary.view.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel

class LoadingViewModel : BaseViewModel() {

    private val _isShowing = MutableLiveData<Boolean>()
    val isShowing : LiveData<Boolean> get() = _isShowing

    init {
        _isShowing.value = false
    }

    fun show() {
        _isShowing.value = true
    }

    fun hide() {
        _isShowing.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}