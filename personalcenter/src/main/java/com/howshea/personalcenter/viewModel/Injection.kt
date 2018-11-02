package com.howshea.personalcenter.viewModel

import android.content.Context
import com.howshea.basemodule.database.CollectionDataBase

/**
 * Created by Howshea
 * on 2018/11/2
 */
object Injection {
    private fun provideDaoSource(context: Context) =
        CollectionDataBase.getInstance(context).collectionDao()

    fun provideViewModelFactory(context: Context) =
        ViewModelFactory(provideDaoSource(context))
}