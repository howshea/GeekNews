package com.howshea.personalcenter.ui

import android.support.v7.app.AlertDialog
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.toast
import com.howshea.personalcenter.R
import com.howshea.personalcenter.util.clearCache
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.personal_fragment.*

/**
 * Created by Howshea
 * on 2018/10/29
 */
class PersonalFragment : LazyFragment() {
    override fun getData() {
        tv_about.setOnClickListener {

        }
        tv_clear.setOnClickListener {
            AlertDialog.Builder(activity!!)
                .setMessage("所有缓存都会被清除")
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("确认") { dialog, _ ->
                    Observable
                        .create<Boolean> { emitter ->
                            val result = AppContext.clearCache()
                            emitter.onNext(result)
                        }
                        .dispatchDefault()
                        .subscribeBy(onNext = { result ->
                            dialog.dismiss()
                            toast(if (result) "清除成功" else "清除失败")
                        }, onError = { error ->
                            toast("${error.message}")
                        })
                }
                .create()
                .show()
        }
        tv_collection.setOnClickListener {

        }
        tv_setting.setOnClickListener { }
    }

    override fun initView() {
        toolbar.topPadding = activity!!.getStatusBarHeight()
    }

    override fun getLayoutId() = R.layout.personal_fragment
}