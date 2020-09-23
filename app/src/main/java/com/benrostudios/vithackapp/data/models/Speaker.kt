package com.benrostudios.vithackapp.data.models

import com.google.gson.annotations.SerializedName

data class Speaker(
    var company: String, // SpaceZ
    var designation: String, // CEO
    var endUnix: Long, // 1694700624
    var imageUrl: String, // https://firebasestorage.googleapis.com/v0/b/project-vithack.appspot.com/o/Testing%2Fspeaker3.png?alt=media&token=5da538b5-5441-4d4f-a4d7-b4068c2da9a5
    var name: String, // Elon Musk
    var sessionUrl: String, // https://www.youtube.com/watch?v=Ycnz60960W4
    var startUnix: Long // 1594800000
) {
    constructor() : this("", "", 0, "", "", "", 0)
}