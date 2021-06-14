package com.nab.contacts.modelView.repo

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.nab.contacts.model.Contact

class ContactsRepository(
    private val context: Context?
) {

    fun getContacts(): ArrayList<Contact> {
        val arrayData = arrayListOf<Contact>()
        val contentResolver = context!!.contentResolver

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null, null
        )

        cursor.use { c ->
            if (c != null) {
                c.moveToFirst()
                do {
                    val id =
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))

                    val name =
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

                    val number = readFromNumber(contentResolver, id)

                    val imageUri: String? =
                        c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    if (imageUri != null)
                        arrayData.add(Contact(id, name, number, imageUri))
                    else
                        arrayData.add(Contact(id, name, number, null))
                } while (c.moveToNext())
            }

        }
        return arrayData
    }

    private fun readFromNumber(contentResolver: ContentResolver, ID: String): String {
        val phoneCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
            arrayOf(ID),
            null
        )
        var number = ""
        if (phoneCursor!!.moveToFirst())
            number =
                phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

        phoneCursor.close()
        return number
    }
}