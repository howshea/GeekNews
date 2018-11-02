package com.howshea.personalcenter.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.howshea.basemodule.database.CollectionDao

/**
 * Created by Howshea
 * on 2018/11/2
 */
class ViewModelFactory(private val dataSource: CollectionDao):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionViewModel::class.java)) {
            return CollectionViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}