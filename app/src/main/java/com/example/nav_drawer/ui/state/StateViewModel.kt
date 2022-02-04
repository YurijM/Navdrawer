package com.example.nav_drawer.ui.state

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class StateViewModel : ViewModel() {
    private val _state = MutableLiveData<State>().apply {
        value = State(
            value = 0,
            color = Color.rgb(255, 0, 0),
            visible = true
        )
    }
    val state: LiveData<State> = _state

    fun increment() {
        val oldState = _state.value
        _state.value = oldState?.copy(
            value = oldState.value + 1
        )
    }

    fun setColor() {
        val oldState = _state.value
        _state.value = oldState?.copy(
            color = Color.rgb(
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            )
        )
    }

    fun switchVisible() {
        val oldState = _state.value
        _state.value = oldState?.copy(
            visible = !oldState.visible
        )
    }

    data class State (
        val value: Int,
        val color: Int,
        val visible: Boolean
    )
}