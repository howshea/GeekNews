package com.howshea.read.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.read.model.Feed
import com.howshea.read.repository.ReadService
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception

/**
 * Created by Howshea
 * on 2018/10/25
 */
class FeedViewModel : RxViewModel() {
    private lateinit var typeId: String
    private val feedLiveDate: MutableLiveData<List<Feed.Results>> = MutableLiveData()
    private val rxError: MutableLiveData<Throwable> = MutableLiveData()
    fun getFeed(): LiveData<List<Feed.Results>> = feedLiveDate
    fun getError(): LiveData<Throwable> = rxError


    fun refresh(typeId: String) {
        this.typeId = typeId
        ReadService.getFeed(typeId, 20, 1)
            .uniformDispose()
    }

    fun requestData(page: Int) {
        ReadService.getFeed(typeId, 20, page)
            .uniformDispose()
    }

    private fun Observable<Feed>.uniformDispose() {
        this.dispatchDefault()
            .subscribeBy(
                onNext = {
                    if (it.error || it.results.isEmpty()) {
                        rxError.value = Exception("服务器错误")
                    } else {
                        feedLiveDate.value = it.results
                    }
                },
                onError = {
                    rxError.value = it
                }
            )
            .addDispose()
    }
}