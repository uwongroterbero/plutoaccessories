package com.example.plutoaccessories.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.plutoaccessories.R

class ProfileActivity : AppCompatActivity() {
    var tvNama: TextView? = null
    var tvEmail: TextView? = null
    var tvAlamat: TextView? = null
    var tvNoHp: TextView? = null
    var btnLogout: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvNama = findViewById<TextView?>(R.id.tvNama)
        tvEmail = findViewById<TextView?>(R.id.tvEmail)
        tvAlamat = findViewById<TextView?>(R.id.tvAlamat)
        tvNoHp = findViewById<TextView?>(R.id.tvNoHp)
        btnLogout = findViewById<Button?>(R.id.btnLogout)

        // Ambil data user dari SharedPreferences
        val preferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val nama: String = preferences.getString("nama", "Tidak diketahui")!!
        val email: String = preferences.getString("email", "-")!!
        val alamat: String = preferences.getString("alamat", "-")!!
        val noHp: String = preferences.getString("no_hp", "-")!!

        // Tampilkan ke TextView
        tvNama!!.setText(nama)
        tvEmail!!.setText(email)
        tvAlamat!!.setText(alamat)
        tvNoHp!!.setText(noHp)

        // Tombol Logout
        btnLogout!!.setOnClickListener(View.OnClickListener { v: View? ->
            val editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        })
    }
}