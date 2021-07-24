package com.mvi.contacts.network

import com.mvi.contacts.data.ContactModel
import com.mvi.contacts.data.ResponseModel
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @GET("get_contacts")
    suspend fun getContacts(): Response<ContactModel>
    @FormUrlEncoded
    @POST("register_user")
    suspend fun addContact(
        @Field("first_name") firstName: String?,
        @Field("middle_name") middleName: String?,
        @Field("last_name") lastName: String?,
        @Field("mobile_no") mobileNo: String?,
        @Field("email_id") emailId: String?,
        @Field("address") address: String?,
        @Field("profile_image") profileImage: String?
    ): Response<ResponseModel>
    @FormUrlEncoded
    @POST("register_user")
    suspend fun addContact(
        @Field("first_name") firstName: String?,
        @Field("middle_name") middleName: String?,
        @Field("last_name") lastName: String?,
        @Field("mobile_no") mobileNo: String?,
        @Field("email_id") emailId: String?,
        @Field("address") address: String?,
    ): Response<ResponseModel>
    @FormUrlEncoded
    @POST("update_user")
    suspend fun updateContact(
        @Field("id") id: Int?,
        @Field("first_name") firstName: String?,
        @Field("middle_name") middleName: String?,
        @Field("last_name") lastName: String?,
        @Field("mobile_no") mobileNoOne: String?,
        @Field("email_id") emailId: String?,
        @Field("address") address: String?,
        @Field("profile_image") profileImage: String?
    ): Response<ResponseModel>
    @FormUrlEncoded
    @POST("update_user")
    suspend fun updateContact(
        @Field("id") id: Int?,
        @Field("first_name") firstName: String?,
        @Field("middle_name") middleName: String?,
        @Field("last_name") lastName: String?,
        @Field("mobile_no") mobileNoOne: String?,
        @Field("email_id") emailId: String?,
        @Field("address") address: String?,
    ): Response<ResponseModel>
}