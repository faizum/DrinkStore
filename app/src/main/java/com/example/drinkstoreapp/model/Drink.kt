package com.example.drinkstoreapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "drink_table")
data class Drink(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val phoneNumber:String,
    val latitude: Double?,
    val longitude:Double?
) : Parcelable