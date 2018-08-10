package com.howshea.home.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.home.model.Common
import com.howshea.home.model.Results
import com.howshea.home.repository.HomeService
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Howshea
 * on 2018/7/16.
 */
class DailyViewModel : RxViewModel() {
    private val dailyData: MutableLiveData<List<Common>> = MutableLiveData()


    fun getTodayData(): LiveData<List<Common>> {
        return dailyData
    }

    fun refresh() = getToday()

    private fun getToday() {
        HomeService.getToday()
            .dispatchDefault()
            .subscribeBy(
                onNext = { data ->
                    val tempList: MutableList<Common> = arrayListOf()
                    with(data.results) {
                        android?.let { tempList += it }
                        ios?.let { tempList += it }
                        frontEnd?.let { tempList += it }
                        app?.let { tempList += it }
                        sources?.let { tempList += it }
                        recommend?.let { tempList += it }
                    }
                    dailyData.value = tempList
                },
                onError = {}
            )
            .addDispose()
    }
}