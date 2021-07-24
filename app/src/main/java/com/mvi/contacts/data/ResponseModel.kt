package com.mvi.contacts.data

import com.google.gson.annotations.SerializedName

class ResponseModel {
    @SerializedName("status")
    var status: Int = -1
    @SerializedName("message")
    var message: String? = null
}