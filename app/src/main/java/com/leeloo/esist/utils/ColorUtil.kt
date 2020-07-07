package com.leeloo.esist.utils

import com.leeloo.esist.R

private val colors = intArrayOf(
    R.color.red_500,
    R.color.green_500,
    R.color.indigo_500,
    R.color.light_blue_500,
    R.color.orange_500,
    R.color.purple_500,
    R.color.teal_500
)

fun getRandomColor() = colors.random()