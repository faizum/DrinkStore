package com.example.drinkstoreapp.repository

import com.example.drinkstoreapp.dao.DrinkDao
import com.example.drinkstoreapp.model.Drink
import kotlinx.coroutines.flow.Flow

class DrinkRepository(private val drinkDao: DrinkDao) {
    val allDrinks: Flow<List<Drink>> = drinkDao.getAllDrink()

    suspend fun insertDrink(drink: Drink){
        drinkDao.insertDrink(drink)
    }

    suspend fun deleteDrink(drink: Drink){
        drinkDao.deleteDrink(drink)
    }

    suspend fun updateDrink(drink: Drink){
        drinkDao.updateDrink(drink)
    }
}