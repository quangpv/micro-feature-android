package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.FeatureMediator
import com.example.core.Mediator

class HomeViewModel : ViewModel() {
    val mediator: Mediator = FeatureMediator(viewModelScope)
}
