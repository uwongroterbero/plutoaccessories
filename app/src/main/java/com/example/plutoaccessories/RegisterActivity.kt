package com.example.plutoaccessories.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.plutoaccessories.R
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etNoHp: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var progressDialog: ProgressDialog

    companion object {
        // Gunakan IP lokal emulator (ubah jika pakai HP fisik)
        private const val URL_REGISTER = "http://10.0.2.2/pluto_api/register_user.php"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etAlamat = findViewById(R.id.etAlamat)
        etNoHp = findViewById(R.id.etNoHp)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Mendaftarkan akun...")
        progressDialog.setCancelable(false)

        btnRegister.setOnClickListener { registerUser() }

        btnLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser() {
        val nama = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val alamat = etAlamat.text.toString().trim()
        val no_hp = etNoHp.text.toString().trim()

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Nama, Email, dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.show()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_REGISTER,
            Response.Listener { response ->
                progressDialog.dismiss()
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getInt("success")
                    val message = jsonObject.getString("message")

                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                    if (success == 1) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing data!", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                progressDialog.dismiss()
                Toast.makeText(this, "Koneksi gagal: ${error.message}", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nama"] = nama
                params["email"] = email
                params["password"] = password
                params["alamat"] = alamat
                params["no_hp"] = no_hp
                return params
            }
        }

        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }
}
