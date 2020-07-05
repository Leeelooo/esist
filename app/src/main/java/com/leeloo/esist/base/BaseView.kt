package com.leeloo.esist.base

import kotlinx.coroutines.flow.Flow

interface BaseView<VS : BaseViewState, I : BaseIntent<*>> {
    fun render(viewState: VS)
    fun intentFlow(): Flow<I>
}