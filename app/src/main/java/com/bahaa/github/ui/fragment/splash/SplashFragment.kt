package com.bahaa.github.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.bahaa.github.R
import com.bahaa.github.databinding.ActivitySplashBinding
import com.bahaa.github.ui.fragment.base.BaseFragment

class SplashFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = DataBindingUtil.inflate<ActivitySplashBinding>(inflater, R.layout.activity_splash, container, false)
        Handler().postDelayed({ navigate(R.id.action_splashFragment_to_navHome) }, 3000)
        return view.root
    }
}
