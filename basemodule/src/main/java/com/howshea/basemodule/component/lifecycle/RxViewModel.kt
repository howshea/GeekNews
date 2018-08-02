package com.howshea.basemodule.component.lifecycle

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class RxViewModel : ViewModel() {
    //收集 RxJava 的订阅事件统一处理
    private val disposes = CompositeDisposable()

    override fun onCleared() {
        disposes.clear()
        super.onCleared()
    }

    protected fun Disposable.addDispose() {
        disposes.add(this)
    }
}