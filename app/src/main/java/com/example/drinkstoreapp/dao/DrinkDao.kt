package com.example.drinkstoreapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.drinkstoreapp.model.Drink
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {
    @Query("SELECT * FROM `drink_table` ORDER BY name ASC")
    fun getAllDrink(): Flow<List<Drink>>

    @Insert
    suspend fun insertDrink(drink: Drink)

    @Delete
    suspend fun deleteDrink(drink: Drink)

    @Update fun updateDrink(drink: Drink)
}