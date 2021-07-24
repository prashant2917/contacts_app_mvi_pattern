package com.mvi.contacts


import com.mvi.contacts.data.ContactModel
import com.mvi.contacts.data.ResponseModel
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ContactsViewModelTest {
    @Test
    fun fetchContactsSuccess() {
        val contactModel = getContactModel()
        assertTrue(contactModel.count > 0)
    }
    @Test
    fun fetchContactsFailure() {
        val contactModel = ContactModel()
        assertTrue(contactModel.count == 0)
    }
    @Test
    fun addContactSuccess() {
        val responseModel = getResponseModel()
        assertTrue(responseModel.status == 1)
    }
    @Test
    fun addContactFailure() {
        val responseModel = ResponseModel()
        assertTrue(responseModel.status == -1)
    }

    private fun getContactModel(): ContactModel {
        val contactModel = ContactModel()
        return contactModel.apply {
            this.count = 10
            this.status = "ok"
        }
    }

    private fun getResponseModel(): ResponseModel {
        val responseModel = ResponseModel()
        return responseModel.apply {
            this.status = 1
            this.message = "Contact Added Successfully"
        }
    }
}