package com.example.drinkstoreapp.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.drinkstoreapp.R
import com.example.drinkstoreapp.application.DrinkApp
import com.example.drinkstoreapp.databinding.FragmentSecondBinding
import com.example.drinkstoreapp.model.Drink
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val drinkViewModel: DrinkViewModel by viewModels {
        DrinkViewModelFactory((applicationContext as DrinkApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var drink: Drink? = null
    private lateinit var mMap : GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drink = args.drink
        // jika drink null maka tampilan default add drink store
        // jika drink tidak null tampilan berubah ada tombol add dan update
        if (drink != null){
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Update"
            binding.nameEditText.setText(drink?.name)
            binding.addressEditText.setText(drink?.address)
            binding.phoneNumberEditText.setText(drink?.phoneNumber)
        }

        //binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name= binding.nameEditText.text
        val address = binding.addressEditText.text
        val phoneNumber = binding.phoneNumberEditText.text
        binding.saveButton.setOnClickListener {
            // kondisi jika field name, address, phoneNumber kosong makan tidak bisa di save
            if (name.isEmpty()) {
                Toast.makeText(context, "Name can't be empty", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Address can't be empty", Toast.LENGTH_SHORT).show()
            } else if (phoneNumber.isEmpty()) {
                Toast.makeText(context, "Phone Number can't be empty", Toast.LENGTH_SHORT).show()
            } else {
                if (drink == null) {
                    val drink =
                        Drink(0, name.toString(), address.toString(), phoneNumber.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    drinkViewModel.insert(drink)
                } else {
                    val drink = Drink(drink?.id!!, name.toString(), address.toString(), phoneNumber.toString(),currentLatLang?.latitude, currentLatLang?.longitude)
                    drinkViewModel.update(drink)
                }
                findNavController().popBackStack() // untuk dismis halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            drink?.let { drinkViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap= googleMap
        //implement drag marker

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {}

    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        } else{
            Toast.makeText(applicationContext, "Location access denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation(){
        //jika permission disetujui maka akan berhenti di kondisi if
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null){
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatLang = latLang
                    var title = "Marker"

                    if (drink != null){
                        title = drink?.name.toString()
                        val newCurrentLocation = LatLng(drink?.latitude!!, drink?.longitude!!)
                        latLang = newCurrentLocation
                    }

                    val markerOptions = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_store_32))
                    mMap.addMarker(markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang,15f))
                }
            }
    }
}