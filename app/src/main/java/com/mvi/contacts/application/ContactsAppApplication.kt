package com.mvi.contacts.application

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class ContactsAppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}