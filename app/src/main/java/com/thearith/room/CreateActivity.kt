package com.thearith.room

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thearith.room.db.Contact
import com.thearith.room.db.ContactDao

class CreateActivity : AppCompatActivity() {

    private var extraContact: Contact? = null
    private lateinit var contactDao: ContactDao
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        extraContact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DATA", Contact::class.java)
        } else {
            intent.getParcelableExtra("DATA")
        }
        initialize()
    }

    fun onSave(v: View) {
        val contact = edtName.text.toString()
        val phone = edtPhone.text.toString()

        if (contact.isEmpty()) {
            toast("Please input contact name")
            return
        }

        if (phone.isEmpty()) {
            toast("Please input phone number")
            return
        }

        if (phone.length < 9) {
            toast("Minimum is 9 characters")
            return
        }

        saveToDB(contact, phone)
    }

    private fun initialize() {
        contactDao = (application as AppApplication).db.contactDao()
        edtName = findViewById(R.id.edt_name)
        edtPhone = findViewById(R.id.edt_phone_number)

        if (extraContact != null) {
            title = "Update contact"
            edtName.setText(extraContact?.name)
            edtPhone.setText(extraContact?.phone)
        }
    }

    private fun saveToDB(name: String, phone: String) {
        val message = if (extraContact != null) {
            contactDao.update(extraContact?.id!!, name, phone)
            "Contact is updated!"
        } else {
            contactDao.insert(Contact(name = name, phone = phone))
            "Contact is inserted!"
        }
        toast(message)
        finish()
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}