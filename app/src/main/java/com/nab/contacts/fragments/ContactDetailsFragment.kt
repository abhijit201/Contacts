package com.nab.contacts.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.nab.contacts.R
import com.nab.contacts.databinding.FragmentSecondBinding
import com.nab.contacts.model.Contact
import com.nab.contacts.modelView.ContactsViewModel
import com.nab.contacts.modelView.factory.ContactsViewModelFactory
import com.nab.contacts.modelView.repo.ContactsRepository

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ContactDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentSecondBinding
    private lateinit var model: ContactsViewModel

    private lateinit var name: String
    private lateinit var number: String
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun updateUI(item: Contact) {
        if (item.imageUri != null) binding.photo.setImageURI(Uri.parse(item.imageUri))
        else binding.photo.setImageResource(R.drawable.ic_person)

        name = item.name
        number = item.number

        binding.name.text = name
        binding.number.text = number

        binding.call.setOnClickListener {
            val intentCall = Intent(Intent.ACTION_VIEW)
            intentCall.data = Uri.parse("tel:" + item.number);
            context?.startActivity(intentCall)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = ContactsRepository(context)
        val factory = ContactsViewModelFactory(repository)

        model = ViewModelProviders.of(requireActivity(), factory).get(ContactsViewModel::class.java)
        model.getSelectedContact().observe(viewLifecycleOwner, { item ->
            run {
                updateUI(item)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1005 -> {
                Log.d("onActivityResult: ", "onActivityResult: called")
                model.getContacts()
                model.getSelectedContact().observe(viewLifecycleOwner, { item ->
                    run {
                        updateUI(item)
                    }
                })
            }
        }
    }
}