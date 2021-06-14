package com.nab.contacts.modelView.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nab.contacts.modelView.ContactsViewModel
import com.nab.contacts.modelView.repo.ContactsRepository

class ContactsViewModelFactory (
    private val repository: ContactsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactsViewModel(repository) as T
    }
}