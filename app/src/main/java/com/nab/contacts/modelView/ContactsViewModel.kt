package com.nab.contacts.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nab.contacts.model.Contact
import com.nab.contacts.modelView.repo.ContactsRepository

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    private val selectedContact = MutableLiveData<Contact>()

    val contacts: LiveData<List<Contact>> get() = _contacts

    fun getContacts() {
        val contacts = repository.getContacts()
        _contacts.value = contacts
    }

    fun setSelectedContact(contact: Contact) {
        selectedContact.value = contact
    }

    fun getSelectedContact(): MutableLiveData<Contact> {
        return selectedContact
    }
}