package com.example.kotlin_sharedprefs

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_sharedprefs.SharedPrefs.init
import com.example.kotlin_sharedprefs.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        init(this)

        binding.btnSignUp.setOnClickListener {
            val usernameString = binding.etUsername.text.toString().trim()
            val emailString = binding.etEmail.text.toString().trim()
            val passwordString = binding.etPassword.text.toString().trim()

            if (usernameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {
                Toast.makeText(this, "please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = UserModel(usernameString, emailString, passwordString)
            SharedPrefs.saveUser(user)
            SharedPrefs.setIsLoggedIn(false)

            Toast.makeText(this, "Sign Up Successful! Please login.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))

        }

    }
}