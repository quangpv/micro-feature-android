package com.example.home.features

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.core.block
import com.example.core.view
import com.example.home.HomeMediator
import com.example.home.R

class StatusWindowFeature : HomeFeature {

    override fun invoke(fragment: Fragment, mediator: HomeMediator) = block(fragment) {

        mediator.loading.observe(viewLifecycleOwner) {
            view(R.id.progress).visibility = if (it) View.VISIBLE else View.GONE
        }

        mediator.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message ?: "Unknown", Toast.LENGTH_SHORT).show()
        }
    }
}