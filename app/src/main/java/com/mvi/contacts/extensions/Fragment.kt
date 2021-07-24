package com.mvi.contacts.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mvi.contacts.R
import java.io.ByteArrayOutputStream
import java.net.URL

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.loadImageFromGlide(url: String, imageView: ImageView) {
    context?.let {
        context
        Glide.with(it).load(url)
            .placeholder(R.drawable.ic_dummy_profile_pic)
            .into(imageView)
    }
}

fun Fragment.loadImageFromGlide(uri: Uri, imageView: ImageView) {
    context?.let {
        context
        Glide.with(it).load(uri)
            .placeholder(R.drawable.ic_dummy_profile_pic)
            .into(imageView)
    }
}

fun Fragment.convertToBase64(uri: Uri): String {
    val baos = ByteArrayOutputStream()
    val stream = context?.contentResolver?.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(stream)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageBytes: ByteArray = baos.toByteArray()
    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
}

fun convertToBase64(url: String): String {
    val baos = ByteArrayOutputStream()
    val url = URL(url)
    val stream = url.openStream()
    val bitmap = BitmapFactory.decodeStream(stream)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageBytes: ByteArray = baos.toByteArray()
    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
}


