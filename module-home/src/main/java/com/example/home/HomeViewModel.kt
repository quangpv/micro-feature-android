package com.example.home

import androidx.lifecycle.ViewModel
import com.example.core.Mediator
import com.example.core.MediatorDelegate

class HomeViewModel : ViewModel(), Mediator by MediatorDelegate()
