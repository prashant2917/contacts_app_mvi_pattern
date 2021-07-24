package com.mvi.contacts.interfaces

import com.mvi.contacts.data.Contact

interface ItemClickListener {
fun onItemClick(contact: Contact)
}