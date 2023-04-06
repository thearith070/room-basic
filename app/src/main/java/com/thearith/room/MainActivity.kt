package com.thearith.room

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thearith.room.db.ContactDao

class MainActivity : AppCompatActivity() {

    private lateinit var contactDao: ContactDao
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvNoData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactDao = (application as AppApplication).db.contactDao()
        setListContact()
        setupListener()

    }

    private fun setListContact() {
        contactAdapter = ContactAdapter(contactDao)
        tvNoData = findViewById(R.id.tv_no_data)
        recyclerView = findViewById(R.id.rv)
        recyclerView.adapter = contactAdapter
        contactAdapter.onEdit { pos ->
            val intent = Intent(this, CreateActivity::class.java).apply {
                    putExtra("DATA", contactAdapter.currentList[pos])
                }
            startActivity(intent)
        }
        contactDao.getAll().observe(this) {
            tvNoData.isVisible = it.isEmpty()
            contactAdapter.submitList(it)
        }
    }

    private fun setupListener() {
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }
}