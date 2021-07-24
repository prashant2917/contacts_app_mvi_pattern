package com.mvi.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.contacts.intent.ContactsIntent
import com.mvi.contacts.repository.NetworkRepository
import com.mvi.contacts.state.ContactState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ContactsViewModel : ViewModel() {
    val contactsIntent = Channel<ContactsIntent>(Channel.UNLIMITED)
    val state = MutableStateFlow<ContactState>(ContactState.Idle)

    init {
        handleContactsIntent()
    }

    private fun handleContactsIntent() {
        viewModelScope.launch {
            contactsIntent.consumeAsFlow().collect { contactsIntent ->
                when (contactsIntent) {
                    is ContactsIntent.FetchContacts -> {
                        fetchContacts()
                    }
                }
            }
        }
    }

    private fun fetchContacts() {
        viewModelScope.launch(Dispatchers.Main) {
            state.value = ContactState.Loading
            NetworkRepository.fetchContacts().collect { response ->
                if (response.isSuccessful) {
                    response.body().let {
                        if (it?.status == "ok")
                            state.value = ContactState.Success(it)
                        else {
                            state.value = ContactState.Error(it?.status.toString())
                        }
                    }
                } else {
                    state.value = ContactState.Error(response.errorBody().toString())
                }
            }
        }
    }
}