package com.hyden.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<B : ViewDataBinding>(private val layoutId : Int) : Fragment() {

    lateinit var binding : B
    lateinit var compositeDisposable: CompositeDisposable
    open fun initBind() { }
    open fun observing() { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
//        lifeCylceLog("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        binding.lifecycleOwner = this
        lifeCylceLog("onCreateView")
        initBind()
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifeCylceLog("onDestroyView")
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
//        lifeCylceLog("onDestroy")
        compositeDisposable.dispose()
    }


    override fun onStart() {
        super.onStart()
        lifeCylceLog("onStart")
    }

    override fun onResume() {
        super.onResume()
        lifeCylceLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        lifeCylceLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        lifeCylceLog("onStop")
    }

    private fun lifeCylceLog(name : String) {
//        LogW("Fragment : ${binding?.lifecycleOwner} / $name")
    }

}