package com.thearith.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thearith.room.db.Contact
import com.thearith.room.db.ContactDao

class ContactAdapter(private val contactDao: ContactDao) :
    ListAdapter<Contact, ContactAdapter.MyViewHolder>(COMPARATOR) {

    private var edit: ((pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_contacts, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binData(position)
    }

    fun onEdit(edit: ((pos: Int) -> Unit)) = apply {
        this.edit = edit
    }

    inner class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun binData(position: Int) {
            val contact = getItem(position)
            view.apply {
                findViewById<TextView>(R.id.tv_name).text = contact.name
                findViewById<TextView>(R.id.tv_phone_number).text = contact.phone

                findViewById<ImageView>(R.id.iv_delete).setOnClickListener {
                    contactDao.delete(contact)
                }
                findViewById<ImageView>(R.id.iv_edit).setOnClickListener {
                    edit?.invoke(position)
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }

        }
    }

}