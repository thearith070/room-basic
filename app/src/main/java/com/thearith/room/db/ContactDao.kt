package com.thearith.room.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAll(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg contact: Contact)

    @Query("UPDATE contacts SET name = :name, phone = :phone WHERE id = :id")
    fun update(id: Int, name: String, phone: String)

    @Delete
    fun delete(contact: Contact)
}