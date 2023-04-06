package com.thearith.room.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contacts")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val name: String? = null,

    val phone: String? = null
): Parcelable