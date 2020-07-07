package com.leeloo.esist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leeloo.esist.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MainFragment())
                .commit()
        }
    }

}