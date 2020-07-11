package com.benrostudios.vithackapp.data.models

data class User(
    val collegeName: String,
    val fcmToken: String,
    val mail: String,
    val name: String,
    val phone: String,
    val regno: String,
    val selectedDomain: String,
    val uid: String
) {
    constructor() : this("", "", "", "", "", "", "", "")
}