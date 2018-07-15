package com.howshea.home.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.howshea.basemodule.utils.getDay
import com.howshea.basemodule.utils.getMonth
import com.howshea.basemodule.utils.getYear
import com.howshea.home.model.Results
import com.howshea.home.repository.HomeService

/**
 * Created by haipo
 * on 2018/7/16.
 */
class DailyViewModel : ViewModel() {
    val dailyData: MutableLiveData<Results> = MutableLiveData()
    fun getToday(){
        HomeService.getDaily(getYear(), getMonth(), getDay())
    }

}