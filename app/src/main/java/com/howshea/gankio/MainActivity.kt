package com.howshea.gankio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.howshea.baseutils.setDarkStatusIcon

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDarkStatusIcon(false)
    }
}
