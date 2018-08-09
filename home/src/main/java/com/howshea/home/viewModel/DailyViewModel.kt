package com.howshea.home.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.home.model.Results
import com.howshea.home.repository.HomeService
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Howshea
 * on 2018/7/16.
 */
class DailyViewModel : RxViewModel() {
    private val dailyData: MutableLiveData<Results> = MutableLiveData()

    fun getTodayData(): LiveData<Results> {
        getToday()
        return dailyData
    }

    fun refresh() = getToday()

    private fun getToday() {
        HomeService.getToday()
            .dispatchDefault()
            .subscribeBy(
                onNext = {
                    println(it.results)
                    dailyData.value = it.results
                },
                onError = {}
            )
            .addDispose()
    }
}