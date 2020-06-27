package com.leeloo.esist.presentation.base

interface BaseModelState<VS : BaseViewState> {
    fun reduce(oldState: VS): VS
}