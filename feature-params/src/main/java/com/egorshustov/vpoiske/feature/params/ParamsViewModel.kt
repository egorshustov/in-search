package com.egorshustov.vpoiske.feature.params

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ParamsViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableState<ParamsState> = mutableStateOf(ParamsState())
    val state: State<ParamsState> = _state

    fun onTriggerEvent(event: ParamsEvent) {
        when (event) {
        }
    }
}