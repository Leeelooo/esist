package com.leeloo.esist.base

interface BaseIntent<A : BaseAction> {
    fun convertToAction(): A
}