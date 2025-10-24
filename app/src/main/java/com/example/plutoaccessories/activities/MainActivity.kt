package com.example.plutoaccessories.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.plutoaccessories.R

class MainActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnProfile: Button
    private lateinit var btnShop: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvWelcome = findViewById(R.id.tvWelcome)
        tvEmail = findViewById(R.id.tvEmail)
        btnProfile = findViewById(R.id.btnProfile)
        btnShop = findViewById(R.id.btnShop)
        btnLogout = findViewById(R.id.btnLogout)

        val preferences: SharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val nama = preferences.getString("nama", "User")
        val email = preferences.getString("email", "user@email.com")

        tvWelcome.text = "Selamat datang, $nama!"
        tvEmail.text = email

        // Tombol Profil
        btnProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Tombol Belanja / Produk
        btnShop.setOnClickListener {
            val intent = Intent(this@MainActivity, ShopActivity::class.java)
            startActivity(intent)
        }

        // Tombol Logout
        btnLogout.setOnClickListener {
            val editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
