package com.example.drinkstoreapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.drinkstoreapp.R

class LoginFragment : Fragment() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform login authentication
            val isAuthenticated = authenticateUser(username, password)

            if (isAuthenticated) {
                // Login success, perform necessary actions
                Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show()
                val navController = Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_content_main
                )
                navController.navigate(R.id.action_LoginFragment_to_FirstFragment)
            } else {
                // Login failed, show error message
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        return username == "faizum" && password =="123456"
    }
}
