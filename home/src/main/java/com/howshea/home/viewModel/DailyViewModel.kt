package com.howshea.home.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.howshea.home.model.Results
import com.howshea.home.repository.HomeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
/**
 * Created by Howshea
 * on 2018/7/16.
 */
class DailyViewModel : ViewModel(){
    val dailyData: MutableLiveData<Results> = MutableLiveData()

    fun getToday(){
        HomeService.getToday()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCleared() {
        super.onCleared()
    }
}