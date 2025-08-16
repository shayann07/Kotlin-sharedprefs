package com.example.kotlin_sharedprefs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin_sharedprefs.SharedPrefs.init
import com.example.kotlin_sharedprefs.databinding.ActivityHomeBinding
import com.example.kotlin_sharedprefs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init(this)

        if (SharedPrefs.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.btnSignIn.setOnClickListener {
            val inputEmail = binding.etEmail.text.toString().trim()
            val inputPassword = binding.etPassword.text.toString().trim()

            val saveUser = SharedPrefs.getUser()

            if (saveUser != null && inputEmail == saveUser.email && inputPassword == saveUser.password) {
                SharedPrefs.setIsLoggedIn(true)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvsignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }
}