package com.howshea.personalcenter.ui.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.howshea.basemodule.component.viewGroup.baseAdapter.SimpleDecoration
import com.howshea.basemodule.extentions.topPadding
import com.howshea.basemodule.utils.getStatusBarHeight
import com.howshea.basemodule.utils.setStatusTransAndDarkIcon
import com.howshea.personalcenter.R
import com.howshea.personalcenter.ui.adapter.DragCallback
import com.howshea.personalcenter.ui.adapter.TypeDragHelper
import com.howshea.personalcenter.ui.adapter.TypeSortAdapter
import kotlinx.android.synthetic.main.activity_type_sort.*

class TypeSortActivity : AppCompatActivity() {
    private val typeList = ArrayList<String>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { TypeSortAdapter(typeList,this) }

    companion object {
        private const val typeCount = 8
        private const val KEY_COUNT = "type_count"
        private const val KEY_ITEM = "item_"
        private const val KEY_SORT = "type_sort"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type_sort)
        setStatusTransAndDarkIcon()
        toolbar.topPadding = getStatusBarHeight()
        toolbar.setOnNavClick { onBackPressed() }
        initData()
    }

    private fun initData() {
        val sp = getSharedPreferences(KEY_SORT, Context.MODE_PRIVATE)
        if (sp.getInt(KEY_COUNT, 0) == 0) {
            val editor = sp.edit()
            editor.putInt(KEY_COUNT, typeCount)
            val tempArray = arrayListOf("Android", "iOS", "前端", "App", "拓展资源", "瞎推荐", "福利", "休息视频")
            (0 until typeCount).forEach {
                editor.putString("$KEY_ITEM$it", tempArray[it])
                typeList.add(tempArray[it])
            }
            editor.apply()
        } else {
            (0 until typeCount).forEach { it ->
                typeList.add(sp.getString("$KEY_ITEM$it", ""))
            }
        }
        ryc_sort.layoutManager = LinearLayoutManager(this)
        ryc_sort.addItemDecoration(SimpleDecoration(0))
        ryc_sort.adapter = adapter
        val dragHelper = TypeDragHelper(DragCallback(adapter))
        dragHelper.attachToRecyclerView(ryc_sort)
    }

    override fun onBackPressed() {
        val editor = getSharedPreferences(KEY_SORT, Context.MODE_PRIVATE).edit()
        (0 until typeCount).forEach {
            editor.putString("$KEY_ITEM$it", typeList[it])
        }
        editor.apply()
        super.onBackPressed()
    }
}
