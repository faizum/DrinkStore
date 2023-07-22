package com.example.drinkstoreapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkstoreapp.R
import com.example.drinkstoreapp.application.DrinkApp
import com.example.drinkstoreapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val drinkViewModel: DrinkViewModel by viewModels {
        DrinkViewModelFactory((applicationContext as DrinkApp).repository)
    }

//    private val args : SecondFragmentArgs by navArgs<>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DrinkListAdapter{ drink ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(drink)
            findNavController().navigate(action)
        }

        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        drinkViewModel.allDrinks.observe(viewLifecycleOwner){ drinks ->
            drinks.let {
                if (drinks.isEmpty()){
                    binding.illustrationImageView.visibility = View.VISIBLE
                } else {
                    binding.illustrationImageView.visibility = View.GONE
                }
                adapter.submitList(drinks)
            }
        }

        binding.addFAB.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}