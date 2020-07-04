package com.leeloo.esist.base

import kotlinx.coroutines.flow.Flow

interface BaseRepository<MS : BaseModelState<*>> {
    fun modelStateFlow(): Flow<MS>
}