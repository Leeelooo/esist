package com.leeloo.esist.base

interface BaseModelState<VS : BaseViewState> {
    fun reduce(oldState: VS): VS
}