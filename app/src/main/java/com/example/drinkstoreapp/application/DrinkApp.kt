package com.example.drinkstoreapp.application

import android.app.Application
import com.example.drinkstoreapp.repository.DrinkRepository

class DrinkApp: Application() {
    val database by lazy { DrinkDatabase.getDatabase(this) }
    val repository by lazy { DrinkRepository(database.drinkDao()) }
}