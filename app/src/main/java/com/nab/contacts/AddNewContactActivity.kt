package com.nab.contacts

import android.content.ContentProviderOperation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.nab.contacts.databinding.ActivityAddNewContactBinding
import com.nab.contacts.databinding.ActivityMainBinding

class AddNewContactActivity : AppCompatActivity() {
    private lateinit var firstName: String
    private lateinit var secondName: String
    private lateinit var number: String
    private lateinit var binding: ActivityAddNewContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInf = menuInflater
        menuInf.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                val data = binding.name.text.toString().split(" ")
                firstName = data[0]

                secondName = if (data.size == 2)
                    data[1]
                else
                    ""
                number = binding.number.text.trim().toString()
                saveContact()
                return true
            }
        }
        return false
    }

    private fun saveContact() {
        val operationList = ArrayList<ContentProviderOperation>()
        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, secondName)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, firstName)
                .build()
        )

        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build()
        )
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, operationList)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Try again", "saveContact: ${e.message}")
        }
    }
}