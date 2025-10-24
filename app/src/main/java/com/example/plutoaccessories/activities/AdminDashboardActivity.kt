package com.example.plutoaccessories.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.plutoaccessories.R

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var tvAdminNama: TextView
    private lateinit var tvAdminEmail: TextView
    private lateinit var tvAdminRole: TextView
    private lateinit var btnManageUsers: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        tvAdminNama = findViewById(R.id.tvAdminNama)
        tvAdminEmail = findViewById(R.id.tvAdminEmail)
        tvAdminRole = findViewById(R.id.tvAdminRole)
        btnManageUsers = findViewById(R.id.btnManageUsers)
        btnLogout = findViewById(R.id.btnLogout)

        // Ambil data admin dari SharedPreferences
        val preferences: SharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val nama = preferences.getString("nama", "Admin")
        val email = preferences.getString("email", "admin@email.com")
        val role = preferences.getString("role", "admin")

        // Tampilkan data admin
        tvAdminNama.text = nama
        tvAdminEmail.text = email
        tvAdminRole.text = role

        // Tombol Kelola Pengguna
        btnManageUsers.setOnClickListener {
            val intent = Intent(this@AdminDashboardActivity, ManageUsersActivity::class.java)
            startActivity(intent)
        }

        // Tombol Logout
        btnLogout.setOnClickListener {
            val editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this@AdminDashboardActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}