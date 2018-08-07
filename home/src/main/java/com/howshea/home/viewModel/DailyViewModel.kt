package com.howshea.home.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.home.model.Results
import com.howshea.home.repository.HomeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { dailyData.value = it.results },
                onError = {}
            )
            .addDispose()
    }
}