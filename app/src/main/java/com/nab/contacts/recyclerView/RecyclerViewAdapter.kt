package com.nab.contacts.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.nab.contacts.MainActivity
import com.nab.contacts.R
import com.nab.contacts.fragments.ContactDetailsFragment
import com.nab.contacts.model.Contact
import com.nab.contacts.modelView.ContactsViewModel
import com.nab.contacts.modelView.factory.ContactsViewModelFactory
import com.nab.contacts.modelView.repo.ContactsRepository
import com.nab.contacts.replaceFragment

class RecyclerViewAdapter(
    private val arrayList: List<Contact>,
    private val mainActivity: MainActivity,
) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.contact_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.nameTV.text = arrayList[position].name
        holder.numberTV.text = arrayList[position].number

        val repository = ContactsRepository(holder.container.context)
        val factory = ContactsViewModelFactory(repository)
        val model = ViewModelProviders.of(mainActivity, factory).get(ContactsViewModel::class.java)

        holder.container.setOnClickListener {
            model.setSelectedContact(arrayList[position])
            mainActivity.replaceFragment(ContactDetailsFragment(), true)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var container: ConstraintLayout = itemView.findViewById(R.id.item_container)
    var nameTV: TextView = itemView.findViewById(R.id.name)
    var numberTV: TextView = itemView.findViewById(R.id.number)
}