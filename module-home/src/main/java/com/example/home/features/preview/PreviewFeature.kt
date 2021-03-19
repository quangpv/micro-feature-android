package com.example.home.features.preview

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Form
import com.example.core.ViewRegistrable
import com.example.core.getViewModel
import com.example.core.viewBy
import com.example.home.HomeMediator
import com.example.home.R
import com.example.home.features.HomeFeature
import kotlinx.coroutines.launch
import kotlin.random.Random

class PreviewFeature : HomeFeature, ViewRegistrable {

    @SuppressLint("SetTextI18n")
    override fun invoke(fragment: Fragment, mediator: HomeMediator) = onViewCreated(fragment) {
        val viewModel = getViewModel<PreviewViewModel>()
        val labelHome = viewBy<TextView>(R.id.labelHomePage).apply { visibility = View.VISIBLE }

        labelHome.setOnClickListener {
            viewModel.loadPreview(mediator.collectForm.send(Form()))
        }

        viewModel.preview.observe(viewLifecycleOwner) {
            labelHome.text = it
        }
        viewModel.loadError.observe(viewLifecycleOwner) {
            labelHome.text = it
        }

        mediator.loggedIn.observe(viewLifecycleOwner) {
            labelHome.text = "This is home page (Hello $it)"
        }

        mediator.loggedOut.observe(viewLifecycleOwner) {
            labelHome.text = "This is home page"
        }
    }

    override fun onDestroyView() {
        Log.e("Clear", "Preview Feature")
    }
}

class PreviewViewModel : ViewModel() {
    val preview = MutableLiveData<String>()
    val loadError = MutableLiveData<String>()

    fun loadPreview(form: Form) {
        if (!form.isValid) {
            loadError.value = "Home page with error ${form.errorMessage}"
            return
        }
        viewModelScope.launch {
            val formData = form.build()
            preview.value =
                "Home page with params ${formData.values.joinToString()} ${Random.nextInt()}"
        }
    }
}