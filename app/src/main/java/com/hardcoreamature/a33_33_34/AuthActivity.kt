package com.hardcoreamature.a33_33_34

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var authButton: MaterialButton
    private lateinit var toggleTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var isLoginMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Check if user is already signed in
        if (auth.currentUser != null) {
            // User already signed in, go to MainActivity
            navigateToMainActivity()
            return
        }

        setContentView(R.layout.activity_auth)
        initializeUI()
    }

    private fun initializeUI() {
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        authButton = findViewById(R.id.buttonAuth)
        toggleTextView = findViewById(R.id.textViewToggle)
        progressBar = findViewById(R.id.progressBar)

        authButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInput(email, password)) {
                if (isLoginMode) {
                    loginUser(email, password)
                } else {
                    registerUser(email, password)
                }
            }
        }

        toggleTextView.setOnClickListener {
            toggleMode()
        }

        updateUIForMode()
    }

    private fun toggleMode() {
        isLoginMode = !isLoginMode
        updateUIForMode()
    }

    private fun updateUIForMode() {
        if (isLoginMode) {
            authButton.text = getString(R.string.login)
            toggleTextView.text = getString(R.string.need_to_register)
        } else {
            authButton.text = getString(R.string.register)
            toggleTextView.text = getString(R.string.already_have_an_account_log_in)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerUser(email: String, password: String) {
        showProgressBar()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            hideProgressBar()
            if (task.isSuccessful) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        showProgressBar()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            hideProgressBar()
            if (task.isSuccessful) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish AuthActivity to prevent returning to it
    }
}
