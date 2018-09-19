package com.howshea.home.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.home.model.HistoryResult
import com.howshea.home.repository.HomeService
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Howshea
 * on 2018/9/19
 */
class PastNewsViewModel : RxViewModel() {
    private val history: MutableLiveData<List<HistoryResult>> = MutableLiveData()
    fun getHistory(): LiveData<List<HistoryResult>> = history

    fun requestData(page: Int) {
        HomeService.getHistory(10, page)
            .flatMap<List<HistoryResult>> { data ->
                data.results.forEach { historyResult ->
                    historyResult.cover = parseSrc(historyResult.cover!!)
                }
                Observable.fromArray(data.results)
            }
            .dispatchDefault()
            .subscribeBy(
                onNext = {
                    history.value = it
                },
                onError = {

                }
            )
    }

    private fun parseSrc(content: String): String? {
        val pattern = """src="([^"]+)"""
        val src = Regex(pattern).find(content)?.value
        return src?.split('\"')?.get(1)
    }
}