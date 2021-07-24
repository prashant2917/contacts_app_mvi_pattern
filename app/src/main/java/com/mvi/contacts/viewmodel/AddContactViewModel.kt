package com.mvi.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.contacts.data.Contact
import com.mvi.contacts.intent.ContactsIntent
import com.mvi.contacts.repository.NetworkRepository
import com.mvi.contacts.state.ContactState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddContactViewModel : ViewModel() {
    val contactsIntent = Channel<ContactsIntent>(Channel.UNLIMITED)
    val state = MutableStateFlow<ContactState>(ContactState.Idle)
    lateinit var contact: Contact

    init {
        handleContactsIntent()
    }

    private fun handleContactsIntent() {
        viewModelScope.launch {
            contactsIntent.consumeAsFlow().collect { contactsIntent ->
                when (contactsIntent) {
                    is ContactsIntent.AddContact -> {
                        addContact(contact)
                    }
                    is ContactsIntent.EditContact -> {
                        updateContact(contact)
                    }
                }
            }
        }
    }

    private fun addContact(contact: Contact) {
        viewModelScope.launch {
            state.value = ContactState.Loading
            NetworkRepository.addContact(contact).collect { response ->
                if (response.isSuccessful) {
                    response.body().let {
                        state.value = if (it?.status == 1) {
                            ContactState.Success(it)
                        } else {
                            ContactState.Error(it?.message)
                        }
                    }
                } else {
                    state.value = ContactState.Error(response.errorBody().toString())
                }
            }
        }
    }

    private fun updateContact(contact: Contact) {
        viewModelScope.launch {
            state.value = ContactState.Loading
            NetworkRepository.updateContact(contact).collect { response ->
                if (response.isSuccessful) {
                    response.body().let {
                        state.value = if (it?.status == 1) {
                            ContactState.Success(it)
                        } else {
                            ContactState.Error(it?.message)
                        }
                    }
                } else {
                    state.value = ContactState.Error(response.errorBody().toString())
                }
            }
        }
    }
}