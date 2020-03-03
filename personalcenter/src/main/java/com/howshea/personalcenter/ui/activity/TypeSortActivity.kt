package com.howshea.personalcenter.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
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
    //记录进入时初始排序
    private val originalList = ArrayList<String>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { TypeSortAdapter(typeList, this) }

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
                originalList.add(tempArray[it])
            }
            editor.apply()
        } else {
            (0 until typeCount).forEach {
                val element = sp.getString("$KEY_ITEM$it", "")?:""
                typeList.add(element)
                originalList.add(element)
            }
        }
        ryc_sort.layoutManager = LinearLayoutManager(this)
        ryc_sort.addItemDecoration(SimpleDecoration(0))
        ryc_sort.adapter = adapter
        val dragHelper = TypeDragHelper(DragCallback(adapter))
        dragHelper.attachToRecyclerView(ryc_sort)
    }

    override fun onBackPressed() {
        if (originalList != typeList)
            AlertDialog.Builder(this)
                .setTitle("确定要修改排序吗")
                .setMessage("返回首页手动刷新即可生效")
                .setPositiveButton("确定") { dialog, _ ->
                    executeSortResult()
                    dialog.dismiss()
                    super.onBackPressed()
                }
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        else {
            super.onBackPressed()
        }
    }

    //排序结果写入sp
    private fun executeSortResult() {
        val editor = getSharedPreferences(KEY_SORT, Context.MODE_PRIVATE).edit()
        (0 until typeCount).forEach {
            editor.putString("$KEY_ITEM$it", typeList[it])
        }
        editor.apply()
    }
}
