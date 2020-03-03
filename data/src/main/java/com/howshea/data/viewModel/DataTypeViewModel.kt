package com.howshea.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.data.model.Data
import com.howshea.data.repository.DataServices
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception

/**
 * Created by Howshea
 * on 2018/10/29.
 */
class DataTypeViewModel : RxViewModel() {
    private lateinit var type: String
    private val rxError: MutableLiveData<Throwable> = MutableLiveData()
    private val dataLiveData: MutableLiveData<List<Data.Results>> = MutableLiveData()

    fun getError(): LiveData<Throwable> = rxError
    fun getTypeData(): LiveData<List<Data.Results>> = dataLiveData

    fun refresh(type: String) {
        this.type = type
        DataServices.getDataOfType(type, 20, 1)
            .uniformDispose()
    }

    fun requestData(page: Int) {
        DataServices.getDataOfType(type, 20, page)
            .uniformDispose()
    }

    private fun Observable<Data>.uniformDispose() {
        this.dispatchDefault()
            .subscribeBy(
                onNext = {
                    if (it.error || it.results.isEmpty()) {
                        rxError.value = Exception("服务器错误")
                    } else {
                        dataLiveData.value = it.results
                    }
                },
                onError = {
                    rxError.value = it
                }
            )
            .addDispose()
    }

}