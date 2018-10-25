package com.howshea.read.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.read.model.MainCategory
import com.howshea.read.repository.ReadService
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception

class ReadViewModel : RxViewModel() {
    private val mainCategory: MutableLiveData<List<MainCategory.Results>> = MutableLiveData()
    private val rxError: MutableLiveData<Throwable> = MutableLiveData()
    fun getCategories(): LiveData<List<MainCategory.Results>> {
        requestData()
        return mainCategory
    }

    fun getError(): LiveData<Throwable> = rxError

    private fun requestData() {
        ReadService.getMainCategory()
            .dispatchDefault()
            .subscribeBy(
                onNext = {
                    if (it.error || it.results.isEmpty()) {
                        rxError.value = Exception("服务器错误")
                    } else {
                        mainCategory.value = it.results
                    }
                },
                onError = {
                    rxError.value = it
                }
            )
            .addDispose()
    }
}
