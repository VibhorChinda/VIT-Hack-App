package com.benrostudios.vithackapp.utils

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.benrostudios.vithackapp.R
import com.google.android.material.snackbar.Snackbar

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

fun EditText.isValidPhone(): Boolean {
    val pattern = Regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}\$")
    val validation: Boolean = pattern.containsMatchIn(this.text.toString())
    return if (validation) {
        true
    } else {
        this.error = "Please enter a valid mobile number"
        false
    }
}

fun EditText.isValidEmail(): Boolean {
    val validation: Boolean =
        (this.text.toString().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this.text.toString())
            .matches())
    return if (validation) {
        true
    } else {
        this.error = "Please enter a valid Email ID"
        false
    }
}

fun View.errorSnackBar(msg: String) {
    val snack = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    snack.setBackgroundTint(resources.getColor(R.color.error_red))
    snack.show()
}

fun View.successSnackBar(msg: String) {
    val snack = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    snack.setBackgroundTint(resources.getColor(R.color.success_green))
    snack.show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Context.imagePlaceholder(): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(this)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.setColorSchemeColors(resources.getColor(R.color.blue_accent))
    circularProgressDrawable.start()
    return circularProgressDrawable
}
