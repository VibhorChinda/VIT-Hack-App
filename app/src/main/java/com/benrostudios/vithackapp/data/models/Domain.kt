package com.benrostudios.vithackapp.data.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Domain(
    var colour: String ="", // #FFB7B2
    var description: String = "", // With an increasing dependence on medical services the need for medical technology is essential. Expand the fields of biotechnology to sustain a better quality of healthcare.
    var domain: String = "", // Healthcare
    var icon: String = "", // https://firebasestorage.googleapis.com/v0/b/project-vithack.appspot.com/o/domains%2Fheathcare.png?alt=media&token=3d2a4d9e-08f8-4ab6-ba95-817d4e899ce8
    var problemStatements: List<String> = emptyList(),
    var zproblemStatements: List<String> = emptyList(),
    var abbreviation: String = ""
): Serializable