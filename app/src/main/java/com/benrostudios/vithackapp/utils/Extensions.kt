package com.benrostudios.vithackapp.utils

import android.app.ProgressDialog.show
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast

fun Context.shortToaster(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToaster(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun EditText.isValidAlphaNumeric(errorDisplay: String): Boolean {
    return if (this.text.isNotEmpty() && this.text.length > 3) {
        true
    } else {
        this.error = "Please enter a valid $errorDisplay"
        false
    }
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun EditText.isValidPhone(): Boolean {
    var validation: Boolean = this.text.contains(Regex("/^(\\+\\d{1,3}[- ]?)?\\d{10}\$/"))
    return if (validation && text.length == 10) {
        true
    } else {
        this.error = "Please enter a valid mobile number"
        false
    }
}