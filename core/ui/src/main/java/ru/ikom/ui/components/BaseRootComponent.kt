package ru.ikom.ui.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

abstract class BaseRootComponent<State : Any, Msg : Any, Label: Any>(initialState: State) : ViewModel() {

    protected val uiState = MutableStateFlow(initialState)

    val state = uiState.asStateFlow()

    private val _labels = Channel<Label>(capacity = Channel.BUFFERED)
    val labels = _labels.receiveAsFlow()

    protected fun dispatch(msg: Msg) {
        uiState.update { it.reduce(msg) }
    }

    protected inline fun dispatch(block: State.() -> State) {
        uiState.update { block(it) }
    }

    protected fun publish(label: Label) {
        _labels.trySend(label)
    }

    protected abstract fun State.reduce(msg: Msg): State
}