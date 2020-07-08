package com.leeloo.esist.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS : BaseViewState, I : BaseIntent<A>, A : BaseAction> : ViewModel() {
    protected abstract val stateFlow: Flow<VS>

    protected abstract suspend fun processAction(action: A)

    fun viewStatesFlow(): Flow<VS> = stateFlow

    fun processIntents(intents: Flow<I>) {
        viewModelScope.launch {
            intents
                .map { it.convertToAction() }
                .collect { processAction(it) }
        }
    }

}