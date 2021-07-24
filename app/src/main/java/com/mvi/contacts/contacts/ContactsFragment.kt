package com.mvi.contacts.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mvi.contacts.adapter.ContactsAdapter
import com.mvi.contacts.databinding.FragmentContactsBinding
import com.mvi.contacts.interfaces.ItemClickListener
import com.mvi.contacts.data.Contact
import com.mvi.contacts.data.ContactModel
import com.mvi.contacts.extensions.showToast
import com.mvi.contacts.intent.ContactsIntent
import com.mvi.contacts.state.ContactState
import com.mvi.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        getContacts()
        observeViewModel()
    }

    private fun init() {
        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)
        binding.fabAdd.setOnClickListener(fabClickListener)
    }

    private val fabClickListener =
        View.OnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(KEY_OBJECT_CONTACT, Contact())
            bundle.putBoolean(KEY_IS_EDIT, false)
            val action = ContactsFragmentDirections.goToAddContact(Contact(), false)
            findNavController().navigate(action)
        }

    private fun getContacts() {
        lifecycleScope.launch {
            contactsViewModel.contactsIntent.send(ContactsIntent.FetchContacts)
        }
    }

    private fun observeViewModel() {
    lifecycleScope.launch {

        contactsViewModel.state.collect { contactState ->
          when(contactState) {
              is ContactState.Idle -> {

              }
              is ContactState.Loading -> {
                  binding.swipeRefreshLayout.isRefreshing = true
              }
              is ContactState.Success -> {
                 val contactModel = contactState.data as ContactModel
                  if (contactModel.status == "ok" && contactModel.count > 0) {
                      binding.contactModel = contactModel
                      contactsAdapter = ContactsAdapter(
                         contactModel.contactList,
                          onItemClickListener
                      )
                      binding.recyclerContacts.adapter = contactsAdapter
                      binding.swipeRefreshLayout.isRefreshing = false

                  }
              }
              is ContactState.Error -> {
                showToast(contactState.error.toString())
              }
          }

        }
    }

    }
    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        getContacts()
    }
    private val onItemClickListener = object : ItemClickListener {
        override fun onItemClick(contact: Contact) {
            val bundle = Bundle()
            bundle.putParcelable(KEY_OBJECT_CONTACT, contact)
            bundle.putBoolean(KEY_IS_EDIT, true)
            val action = ContactsFragmentDirections.goToAddContact(contact, true)
            findNavController().navigate(action)
        }
    }

    companion object {
        const val KEY_OBJECT_CONTACT = "contact"
        const val KEY_IS_EDIT = "is_edit"
    }
}

