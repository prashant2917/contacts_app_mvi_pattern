package com.mvi.contacts.extensions

import android.app.Activity
import android.content.Intent
import android.view.WindowManager

fun Activity.startNewActivity(intent: Intent, isFinish: Boolean) {
    this.startActivity(intent)
    if (isFinish) finish()
}

fun Activity.disableUserInteraction() {
    window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.enableUserInteraction() {
    window?.clearFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}