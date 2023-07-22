package com.example.drinkstoreapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.drinkstoreapp.dao.DrinkDao
import com.example.drinkstoreapp.model.Drink

@Database(entities = [Drink::class], version = 2, exportSchema = false)
abstract class DrinkDatabase: RoomDatabase(){
    abstract fun drinkDao(): DrinkDao

    companion object{
        private var INSTANCE: DrinkDatabase? = null

        //migrasi database versi 1 ke 2, karena ada perubahan table tadi
        private val migration1To2: Migration = object: Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE drink_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE drink_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }

        fun getDatabase(context: Context): DrinkDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinkDatabase::class.java,
                    "drink_database"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}