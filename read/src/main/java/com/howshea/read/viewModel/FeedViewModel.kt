package com.howshea.read.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.read.model.Feed

/**
 * Created by Howshea
 * on 2018/10/25
 */
class FeedViewModel : RxViewModel() {
    private lateinit var enName: String
    private val feedLiveDate: MutableLiveData<List<Feed.Results>> = MutableLiveData()
    private val rxError: MutableLiveData<Throwable> = MutableLiveData()
    fun getFeed(): LiveData<List<Feed.Results>> = feedLiveDate
    fun getError(): LiveData<Throwable> = rxError


    fun refresh(enName: String) {
        this.enName = enName
        //todo
    }

    fun requestData(page: Int) {

    }
}