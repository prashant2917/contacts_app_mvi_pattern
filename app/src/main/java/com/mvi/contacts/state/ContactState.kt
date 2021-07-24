package com.mvi.contacts.state

sealed class ContactState {
    object Idle : ContactState()
    object Loading : ContactState()
    data class Success(val data: Any?) : ContactState()
    data class Error(val error: String?) : ContactState()
}

