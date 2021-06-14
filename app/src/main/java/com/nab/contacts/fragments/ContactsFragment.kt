package com.nab.contacts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nab.contacts.MainActivity
import com.nab.contacts.databinding.FragmentFirstBinding
import com.nab.contacts.modelView.ContactsViewModel
import com.nab.contacts.modelView.factory.ContactsViewModelFactory
import com.nab.contacts.modelView.repo.ContactsRepository
import com.nab.contacts.recyclerView.RecyclerViewAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContactsFragment : Fragment() {

    private lateinit var _binding: FragmentFirstBinding
    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: RecyclerViewAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.recyclerContacts.setHasFixedSize(true)
        binding.recyclerContacts.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = ContactsRepository(context)
        val factory = ContactsViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(ContactsViewModel::class.java)
        viewModel.getContacts()
        viewModel.contacts.observe(viewLifecycleOwner, { contacts ->
            run {
                adapter = RecyclerViewAdapter(contacts, activity as MainActivity)
                binding.recyclerContacts.adapter = adapter
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getContacts()
        adapter.notifyDataSetChanged()
    }
}