package com.example.drinkstoreapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkstoreapp.R
import com.example.drinkstoreapp.model.Drink

class DrinkListAdapter(
    private val onItemClickListener: (Drink) -> Unit
): ListAdapter<Drink, DrinkListAdapter.DrinkViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return  DrinkViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = getItem(position)
        holder.bind(drink)
        holder.itemView.setOnClickListener {
            onItemClickListener(drink)
        }
    }
    class DrinkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val phoneNumberTextView: TextView = itemView.findViewById(R.id.phoneNumberTextView)

        fun bind(drink: Drink?) {
            nameTextView.text = drink?.name
            addressTextView.text = drink?.address
            phoneNumberTextView.text = drink?.phoneNumber
        }

        companion object {
            fun create(parent: ViewGroup): DrinkListAdapter.DrinkViewHolder {
            val view: View = LayoutInflater.from((parent.context))
                .inflate(R.layout.item_drink, parent, false)
                return  DrinkViewHolder(view)
            }
        }

    }

    companion object{
        private val WORDS_COMPARATOR = object :DiffUtil.ItemCallback<Drink>(){
            override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}