package com.example.project9

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project9.databinding.FragmentLoginBinding
import com.example.project9.databinding.FragmentSelfiesBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    /**
     * A simple {@link Fragment} subclass that represents the login screen of the application.
     * It allows users to log in with their email and password using Firebase authentication.
     */
    private val TAG = "LoginFragment"

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * Called to have the fragment instantiate its user interface view.
         *
         * @param inflater           The LayoutInflater object that can be used to inflate views.
         * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
         * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
         * @return The created view or null.
         */

        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            goToSelfiesScreen()
        }
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            /**
             * Handles the click event of the login button.
             * Attempts to authenticate the user with the provided email and password using Firebase authentication.
             */
            btnLogin.isEnabled = false
            val etEmail = binding.etEmail
            val etPassword = binding.etPassword
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }
            // Firebase authentication check
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Success!", Toast.LENGTH_SHORT).show()
                    goToSelfiesScreen()
                } else {
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    private fun goToSelfiesScreen() {
        this.findNavController().navigate(R.id.action_login_to_selfies)
    }
}