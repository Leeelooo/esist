package com.leeloo.esist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.leeloo.esist.R
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class MainFragment : Fragment(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(
            R.layout.fragment_main,
            container,
            false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}