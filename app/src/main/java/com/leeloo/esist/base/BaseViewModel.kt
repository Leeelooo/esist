package com.leeloo.esist.base

import kotlinx.coroutines.flow.Flow

interface BaseViewModel<VS : BaseViewState, I : BaseIntent> {
    fun viewStatesFlow(): Flow<VS>
    fun processIntents(intents: Flow<I>)
}