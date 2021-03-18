package com.example.home.features.preview

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.example.core.block
import com.example.core.observe
import com.example.core.viewBy
import com.example.home.HomeFragment
import com.example.home.R
import com.example.home.features.HomeCommand
import com.example.home.features.HomeFeature

class PreviewFeature : HomeFeature {

    @SuppressLint("SetTextI18n")
    override fun invoke(fragment: HomeFragment) = block(fragment) {
        val labelHome = viewBy<TextView>(R.id.labelHomePage).apply { visibility = View.VISIBLE }

        mediator.observe<HomeCommand.LoggedIn>(viewLifecycleOwner) {
            labelHome.text = "This is home to preview (Hello $userName)"
        }
    }
}