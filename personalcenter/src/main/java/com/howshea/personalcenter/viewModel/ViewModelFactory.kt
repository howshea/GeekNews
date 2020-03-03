package com.howshea.personalcenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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