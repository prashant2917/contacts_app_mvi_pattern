package com.mvi.contacts.contacts

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mvi.contacts.R
import com.mvi.contacts.adapter.ContactsAdapter
import com.mvi.contacts.data.Contact
import com.mvi.contacts.data.ResponseModel
import com.mvi.contacts.databinding.FragmentAddContactsBinding
import com.mvi.contacts.extensions.*
import com.mvi.contacts.intent.ContactsIntent
import com.mvi.contacts.state.ContactState
import com.mvi.contacts.validator.ValidatorFactory
import com.mvi.contacts.validator.ValidatorType
import com.mvi.contacts.viewmodel.AddContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddContactFragment : Fragment() {
    private lateinit var binding: FragmentAddContactsBinding
    private lateinit var addContactViewModel: AddContactViewModel
    private lateinit var contact: Contact
    private var isEdit: Boolean = false
    private var imageUrl: String = ""
    private var imageBase64: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeViewModel()
    }

    private fun init() {
        contact = arguments?.getParcelable(ContactsFragment.KEY_OBJECT_CONTACT)!!
        isEdit = arguments?.getBoolean(ContactsFragment.KEY_IS_EDIT)!!
        addContactViewModel = ViewModelProviders.of(this).get(AddContactViewModel::class.java)
        binding.progressVisibility = View.GONE
        binding.btnSubmit.setOnClickListener(submitListener)
        binding.ivEdit.setOnClickListener(editClickListener)
        if (isEdit) {
            binding.contact = contact
            imageUrl = contact.profileImageUrl.toString()
            if (imageUrl.isNotEmpty()) {
                binding.progressVisibility = View.VISIBLE
                activity?.disableUserInteraction()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        imageBase64 = convertToBase64(ContactsAdapter.PROFILE_PIC_URL + imageUrl)
                    }
                    binding.progressVisibility = View.GONE
                    activity?.enableUserInteraction()
                }
            }
        }

        loadImageFromGlide(ContactsAdapter.PROFILE_PIC_URL + imageUrl, binding.ivProfile)
    }

    private val submitListener = View.OnClickListener {
        val validatorName = ValidatorType.ValidatorTypeName()
        val result = ValidatorFactory.getValidator(validatorName)
            .validate(binding.etFirstName, getString(R.string.error_enter_valid_first_name)) &&
                ValidatorFactory.getValidator(validatorName)
                    .validate(
                        binding.etMiddleName,
                        getString(R.string.error_enter_valid_middle_name)
                    ) &&
                ValidatorFactory.getValidator(validatorName)
                    .validate(
                        binding.etLastName,
                        getString(R.string.error_enter_valid_last_name)
                    ) &&
                ValidatorFactory.getValidator(ValidatorType.ValidatorTypeMobile())
                    .validate(
                        binding.etMobileNumber,
                        getString(R.string.error_enter_valid_mobile_number)
                    ) &&
                ValidatorFactory.getValidator(ValidatorType.ValidatorEmail())
                    .validate(
                        binding.etEmailId,
                        getString(R.string.error_enter_Valid_email_address)
                    ) &&
                ValidatorFactory.getValidator(ValidatorType.ValidatorAddress())
                    .validate(binding.etAddress, getString(R.string.error_enter_Valid_address))

        if (result) {
            val contact = buildContact()

            if (isEdit) {
                updateContact(contact)
            } else {
                addContact(contact)
            }
        }
    }
    private val editClickListener = View.OnClickListener {
        ImagePicker.with(this)
            .galleryOnly()
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun buildContact(): Contact {
        val contactObj = Contact()
        return contactObj.apply {
            if (isEdit) {
                id = contact.id
            }
            firstName = binding.etFirstName.text.toString()
            middleName = binding.etMiddleName.text.toString()
            lastName = binding.etLastName.text.toString()
            mobileNo = binding.etMobileNumber.text.toString()
            emailId = binding.etEmailId.text.toString()
            address = binding.etAddress.text.toString()
            profileImageUrl = imageBase64
        }
    }

    private fun addContact(contact: Contact) {
        lifecycleScope.launch {
            addContactViewModel.contact = contact
            addContactViewModel.contactsIntent.send(ContactsIntent.AddContact)
        }
    }

    private fun updateContact(contact: Contact) {
        lifecycleScope.launch {
            addContactViewModel.contact = contact
            addContactViewModel.contactsIntent.send(ContactsIntent.EditContact)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            addContactViewModel.state.collect { contactState ->
                when (contactState) {
                    is ContactState.Idle -> {
                    }
                    is ContactState.Loading -> {
                        binding.progressVisibility = View.VISIBLE
                        activity?.disableUserInteraction()
                    }
                    is ContactState.Success -> {
                        val responseModel = contactState.data as ResponseModel
                        showToast(responseModel.message.toString())
                        binding.progressVisibility = View.GONE
                        activity?.enableUserInteraction()
                        findNavController().popBackStack()
                    }
                    is ContactState.Error -> {
                        showToast(contactState.error.toString())
                        binding.progressVisibility = View.GONE
                        activity?.enableUserInteraction()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    loadImageFromGlide(fileUri, binding.ivProfile)
                    imageBase64 = convertToBase64(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    showToast(ImagePicker.getError(data))
                }
                else -> {
                    showToast("Task Cancelled")
                }
            }
        }
}