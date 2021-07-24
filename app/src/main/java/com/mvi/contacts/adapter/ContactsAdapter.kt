package com.mvi.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvi.contacts.R
import com.mvi.contacts.databinding.RowContactsBinding
import com.mvi.contacts.interfaces.ItemClickListener
import com.mvi.contacts.data.Contact

class ContactsAdapter(
    private var contactList: List<Contact>,
    private var onItemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = RowContactsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.binding.contact = contactList[position]
        PROFILE_PIC_URL + contactList[position].profileImageUrl
        val imageUrl = contactList[position].profileImageUrl.toString()

            Glide.with(holder.binding.root.context).load(PROFILE_PIC_URL + imageUrl).placeholder(R.drawable.ic_dummy_profile_pic).error(R.drawable.ic_dummy_profile_pic)
                .into(holder.binding.ivProfile)

        holder.binding.root.setOnClickListener {
            onItemClickListener.onItemClick(contactList[position])
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ContactsViewHolder(val binding: RowContactsBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val PROFILE_PIC_URL = "https://pocketappz.com/upload/avatar/"
    }
}



