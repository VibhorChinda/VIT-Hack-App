package com.benrostudios.vithackapp.data.models


import com.google.gson.annotations.SerializedName

data class User(
    val company: String,
    val fcmToken: String,
    val isVitan: Boolean,
    val mail: String,
    val name: String,
    val phone: Int,
    val regno: String,
    val room: String,
    val uid: String
)