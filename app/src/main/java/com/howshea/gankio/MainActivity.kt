package com.howshea.gankio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.howshea.baseutils.setDarkStatusIcon
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDarkStatusIcon(true)
    }
}
