package com.howshea.personalcenter.ui.fragment

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.component.fragment.LazyFragment
import com.howshea.basemodule.extentions.dispatchDefault
import com.howshea.basemodule.utils.toast
import com.howshea.personalcenter.R
import com.howshea.personalcenter.ui.activity.AboutActivity
import com.howshea.personalcenter.ui.activity.CollectionActivity
import com.howshea.personalcenter.ui.activity.SettingActivity
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
            startActivity(Intent(context, AboutActivity::class.java))
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
            startActivity(Intent(context, CollectionActivity::class.java))
        }
        tv_setting.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    override fun initView() {
    }

    override fun getLayoutId() = R.layout.personal_fragment
}