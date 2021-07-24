package com.mvi.contacts.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ContactModel {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("count")
    var count: Int = 0

    @SerializedName("contacts")
    var contactList: List<Contact> = emptyList()


}

class Contact() : Parcelable {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("first_name")
    var firstName: String? = ""

    @SerializedName("middle_name")
    var middleName: String? = ""

    @SerializedName("last_name")
    var lastName: String? = ""

    @SerializedName("address")
    var address: String? = ""

    @SerializedName("mobile_no")
    var mobileNo: String? = ""

    @SerializedName("email_id")
    var emailId: String? = ""

    @SerializedName("profile_image")
    var profileImageUrl: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        firstName = parcel.readString()
        middleName = parcel.readString()
        lastName = parcel.readString()
        address = parcel.readString()
        mobileNo = parcel.readString()
        emailId = parcel.readString()
        profileImageUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(firstName)
        parcel.writeString(middleName)
        parcel.writeString(lastName)
        parcel.writeString(address)
        parcel.writeString(mobileNo)
        parcel.writeString(emailId)
        parcel.writeString(profileImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}