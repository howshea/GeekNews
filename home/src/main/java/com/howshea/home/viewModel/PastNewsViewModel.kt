package com.howshea.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.home.model.History
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
    private val rxError: MutableLiveData<Throwable> = MutableLiveData()

    fun getHistory(): LiveData<List<HistoryResult>> = history
    fun getError(): LiveData<Throwable> = rxError

    fun requestData(page: Int) {
        HomeService.getHistory(20, page).uniformDispose()
    }

    fun refresh() {
        HomeService.getHistory(20, 1).uniformDispose()
    }

    private fun Observable<History>.uniformDispose() {
        this.flatMap<List<HistoryResult>> { data ->
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
                    rxError.value = it
                    it.printStackTrace()
                }
            )
            .addDispose()
    }

    private fun parseSrc(content: String): String? {
        //把封面地址提取出来
        val pattern = """src="([^"]+)"""
        val src = Regex(pattern).find(content)?.value
        return src?.split('\"')?.get(1)
    }
}