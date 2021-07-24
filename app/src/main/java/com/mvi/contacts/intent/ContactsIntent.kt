package com.mvi.contacts.intent

sealed class ContactsIntent {
    object FetchContacts : ContactsIntent()
    object AddContact : ContactsIntent()
    object EditContact : ContactsIntent()
}