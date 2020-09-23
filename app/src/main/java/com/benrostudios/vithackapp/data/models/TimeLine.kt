package com.benrostudios.vithackapp.data.models

data class TimeLine (
    val endUnix: Long = 0L,
    val startUnix: Long = 0L,
    val link: String = "",
    val subtitle: String = "",
    val title: String = ""
)