package com.benrostudios.vithackapp.data.models

data class FAQ(
    val question: String,
    val answer: String
){
    constructor():this("","")
}