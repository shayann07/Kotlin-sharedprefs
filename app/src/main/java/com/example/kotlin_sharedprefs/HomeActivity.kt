package com.example.kotlin_sharedprefs

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin_sharedprefs.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val saveUser = SharedPrefs.getUser()
        if (saveUser != null) {
            binding.tvRecieved.text = "WELCOME " + saveUser.username + "!"
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            SharedPrefs.setIsLoggedIn(false)
            SharedPrefs.clear()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }
}