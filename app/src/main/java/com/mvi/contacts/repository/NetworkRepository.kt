package com.mvi.contacts.repository

import com.mvi.contacts.data.Contact
import com.mvi.contacts.data.ContactModel
import com.mvi.contacts.data.ResponseModel
import com.mvi.contacts.network.ApiInterface
import com.mvi.contacts.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

object NetworkRepository {
    suspend fun fetchContacts(): Flow<Response<ContactModel>> {
        return flow {
            emit(RetrofitBuilder.buildService(ApiInterface::class.java).getContacts())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addContact(contact: Contact): Flow<Response<ResponseModel>> {
        if (contact.profileImageUrl.toString().isEmpty()) {
            return flow {
                emit(
                    RetrofitBuilder.buildService(ApiInterface::class.java).addContact(
                        contact.firstName,
                        contact.middleName,
                        contact.lastName,
                        contact.mobileNo,
                        contact.emailId,
                        contact.address
                    )
                )
            }.flowOn(Dispatchers.IO)
        } else {
            return flow {
                emit(
                    RetrofitBuilder.buildService(ApiInterface::class.java).addContact(
                        contact.firstName,
                        contact.middleName,
                        contact.lastName,
                        contact.mobileNo,
                        contact.emailId,
                        contact.address,
                        contact.profileImageUrl
                    )
                )
            }.flowOn(Dispatchers.IO)
        }
    }

    suspend fun updateContact(contact: Contact): Flow<Response<ResponseModel>> {
        if (contact.profileImageUrl.toString().isEmpty()) {
            return flow {
                emit(
                    RetrofitBuilder.buildService(ApiInterface::class.java).updateContact(
                        contact.id,
                        contact.firstName,
                        contact.middleName,
                        contact.lastName,
                        contact.mobileNo,
                        contact.emailId,
                        contact.address
                    )
                )
            }.flowOn(Dispatchers.IO)
        } else {
            return flow {
                emit(
                    RetrofitBuilder.buildService(ApiInterface::class.java).updateContact(
                        contact.id,
                        contact.firstName,
                        contact.middleName,
                        contact.lastName,
                        contact.mobileNo,
                        contact.emailId,
                        contact.address,
                        contact.profileImageUrl
                    )
                )
            }.flowOn(Dispatchers.IO)
        }
    }
}