package com.mvi.contacts.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvi.contacts.databinding.ActivityMainBinding
import com.mvi.contacts.extensions.enableUserInteraction

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        enableUserInteraction()
    }
}