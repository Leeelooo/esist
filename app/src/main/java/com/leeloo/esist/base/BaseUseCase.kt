package com.leeloo.esist.base

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<MS : BaseModelState<*>> {
    fun modelStateFlow(): Flow<MS>
}