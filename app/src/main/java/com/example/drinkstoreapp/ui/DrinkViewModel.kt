package com.example.drinkstoreapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.drinkstoreapp.model.Drink
import com.example.drinkstoreapp.repository.DrinkRepository
import kotlinx.coroutines.launch

class DrinkViewModel(private val repository: DrinkRepository): ViewModel() {
    val allDrinks: LiveData<List<Drink>> = repository.allDrinks.asLiveData()

    fun insert(drink: Drink) = viewModelScope.launch {
        repository.insertDrink(drink)
    }

    fun delete(drink: Drink) = viewModelScope.launch {
        repository.deleteDrink(drink)
    }

    fun update(drink: Drink) = viewModelScope.launch {
        repository.updateDrink(drink)
    }
}

class DrinkViewModelFactory(private val repository: DrinkRepository): ViewModelProvider.Factory{
    override
    fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((DrinkViewModel::class.java))){
            return DrinkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}