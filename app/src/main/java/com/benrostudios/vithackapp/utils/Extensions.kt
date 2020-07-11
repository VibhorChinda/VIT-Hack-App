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
    val validation: Boolean =  android.util.Patterns.PHONE.matcher(this.text).matches();
    return if (validation && text.length == 13) {
        true
    } else {
        this.error = "Please enter a valid mobile number"
        false
    }
}