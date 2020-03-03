package com.howshea.personalcenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.howshea.basemodule.component.lifecycle.RxViewModel
import com.howshea.basemodule.database.Collection
import com.howshea.basemodule.database.CollectionDao
import com.howshea.basemodule.extentions.dispatchDefault
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Howshea
 * on 2018/11/2
 */
class CollectionViewModel(private val dataSource: CollectionDao) : RxViewModel() {
    private val collections = MutableLiveData<List<Collection>>()
    private val rxError = MutableLiveData<Throwable>()

    fun getCollections(): LiveData<List<Collection>> {
        getSource()
        return collections
    }

    fun getError(): LiveData<Throwable> = rxError

    private fun getSource() {
        dataSource.getCollections()
            .dispatchDefault()
            .subscribeBy(
                onNext = {
                    collections.value = it
                },
                onError = {
                    rxError.value = it
                }
            )
    }
}