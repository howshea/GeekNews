package com.howshea.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.basemodule.extentions.otherwise
import com.howshea.basemodule.extentions.yes
import com.howshea.home.model.Common
import com.howshea.home.model.Daily
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

    companion object {
        private const val typeCount = 8
        private const val KEY_COUNT = "type_count"
        private const val KEY_ITEM = "item_"
        private const val KEY_SORT = "type_sort"
    }

    fun refresh(isComponent: Boolean, date: String) {
        if (isComponent) {
            getDaily(date)
        } else {
            getToday()
        }
    }

    private fun getDaily(date: String) {
        if (date.length != 8) {
            rxError.value = IllegalArgumentException("日期格式不正确")
            return
        }
        val year: Int
        val month: Int
        val day: Int
        try {
            year = date.substring(0..3).toInt()
            month = date.substring(4..5).toInt()
            day = date.substring(6..7).toInt()
        } catch (e: NumberFormatException) {
            rxError.value = NumberFormatException("日期格式不正确")
            return
        }
        HomeService.getDaily(year, month, day)
            .uniformDispose()

    }

    private fun getToday() {
        HomeService.getToday().uniformDispose()
    }

    private fun Observable<Daily>.uniformDispose() {
        this
            .flatMap<List<Common>> { data ->
                dealWithData(data)
            }
            .dispatchDefault()
            .subscribeBy(
                onNext = {
                    dailyData.value = it
                },
                onError = {
                    rxError.value = it
                    it.printStackTrace()
                }
            )
            .addDispose()
    }


    private fun dealWithData(data: Daily): Observable<MutableList<Common>>? {
        val tempList: MutableList<Common> = arrayListOf()
        with(data.results) {
            android?.let { tempList += it }
            ios?.let { tempList += it }
            frontEnd?.let { tempList += it }
            app?.let { tempList += it }
            sources?.let { tempList += it }
            recommend?.let { tempList += it }
            girls?.let { it ->
                //因为接口里把妹子的图片地址放在了 url field 里，为了统一处理，这里把图片地址换个位置
                it.forEach { item ->
                    item.images = mutableListOf(item.url)
                    item.url = ""
                }
                tempList += it
            }
            video?.let { tempList += it }
            val sp = AppContext.getSharedPreferences(KEY_SORT, MODE_PRIVATE)
            if (sp.getInt(KEY_COUNT, 0) != 0) {
                sort(tempList, sp)
            }
        }
        tempList.forEach { item ->
            item.images
                ?.let {
                    it.size == 1
                }
                ?.yes {
                    item.ratio = getRadioAndCache(item.images!![0])
                }
                ?.otherwise {
                    return@forEach
                }
                ?: let {
                    return@forEach
                }

        }
        return Observable.fromArray(tempList)
    }


    private fun sort(tempList: MutableList<Common>, sp: SharedPreferences) {
        var startIndex = 0
        (0 until typeCount).forEach { index ->
            val typeName = sp.getString("$KEY_ITEM$index", "")
            (startIndex until tempList.size).forEach {
                if (tempList[it].type == typeName) {
                    val tempItem = tempList[it]
                    tempList.add(startIndex, tempItem)
                    tempList.removeAt(it + 1)
                    startIndex++
                }
            }
        }
    }
}