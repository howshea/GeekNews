package com.howshea.home.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.basemodule.extentions.otherwise
import com.howshea.basemodule.extentions.yes
import com.howshea.home.model.Common
import com.howshea.home.repository.HomeService
import com.howshea.home.util.getRadioAndCache
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Howshea
 * on 2018/7/16.
 */
class DailyViewModel : RxViewModel() {
    private val dailyData: MutableLiveData<List<Common>> = MutableLiveData()
    private val rxError: MutableLiveData<Throwable> = MutableLiveData()

    fun getTodayData(): LiveData<List<Common>> = dailyData

    fun getError(): LiveData<Throwable> = rxError

    fun refresh() = getToday()

    private fun getToday() {
        HomeService.getToday()
            .flatMap<List<Common>> { data ->
                val tempList: MutableList<Common> = arrayListOf()
                with(data.results) {
                    android?.let { tempList += it }
                    ios?.let { tempList += it }
                    frontEnd?.let { tempList += it }
                    app?.let { tempList += it }
                    sources?.let { tempList += it }
                    recommend?.let { tempList += it }
                    girls?.let { it ->
                        //因为接口里把妹子的图片地址放在了url field里，为了统一处理，这里把图片地址换个位置
                        it.forEach { item ->
                            item.images = mutableListOf(item.url)
                            item.url = ""
                        }
                        tempList += it
                    }
                    video?.let { tempList += it }
                }
                tempList.forEach { item ->
                    item.images
                        ?.let {
                            it.size == 1
                        }
                        ?.yes {
                            item.radio = getRadioAndCache(item.images!![0])
                        }
                        ?.otherwise {
                            return@forEach
                        }
                        ?: let {
                            return@forEach
                        }

                }
                Observable.fromArray(tempList)
            }
            .dispatchDefault()
            .subscribeBy(
                onNext = {
                    dailyData.value = it
                },
                onError = {
                    rxError.value =it
                    it.printStackTrace()
                }
            )
            .addDispose()
    }
}

