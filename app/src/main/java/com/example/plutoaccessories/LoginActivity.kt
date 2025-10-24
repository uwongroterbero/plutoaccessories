package com.example.plutoaccessories.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.plutoaccessories.R
import com.example.plutoaccessories.RegisterActivity
import com.example.plutoaccessories.activities.MainActivity
import com.example.plutoaccessories.utils.SessionManager
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var tvRegister: TextView? = null
    private var progLogin: ProgressBar? = null
    private var session: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = SessionManager(this)
        if (session!!.isLoggedIn()) {
            // Jika sudah login, langsung arahkan sesuai role
            gotoAppropriateActivity(session!!.getRole())
            finish()
            return
        }

        etEmail = findViewById<EditText?>(R.id.etEmail)
        etPassword = findViewById<EditText?>(R.id.etPassword)
        btnLogin = findViewById<Button?>(R.id.btnLogin)
        tvRegister = findViewById<TextView?>(R.id.tvRegister)
        progLogin = findViewById<ProgressBar?>(R.id.progLogin)

        btnLogin!!.setOnClickListener(View.OnClickListener { v: View? -> attemptLogin() })

        tvRegister!!.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        })
    }

    private fun attemptLogin() {
        val email = etEmail!!.getText().toString().trim { it <= ' ' }
        val password = etPassword!!.getText().toString().trim { it <= ' ' }

        if (TextUtils.isEmpty(email)) {
            etEmail!!.setError("Email wajib diisi")
            etEmail!!.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password)) {
            etPassword!!.setError("Password wajib diisi")
            etPassword!!.requestFocus()
            return
        }

        progLogin!!.setVisibility(View.VISIBLE)
        btnLogin!!.setEnabled(false)

        val queue = Volley.newRequestQueue(this)
        val req: StringRequest = object : StringRequest(
            Method.POST, LOGIN_URL,
            Response.Listener { response: String? ->
                progLogin!!.setVisibility(View.GONE)
                btnLogin!!.setEnabled(true)
                try {
                    val obj = JSONObject(response)
                    val success = obj.optInt("success", 0)
                    val message = obj.optString("message", "No message")

                    if (success == 1) {
                        // contoh format user: { "id_user":1, "nama":"Fathur", "email":"fathur@gmail.com", "role":"admin" }
                        val user = obj.getJSONObject("user")
                        val idUser = user.getInt("id_user")
                        val nama = user.getString("nama")
                        val userEmail = user.getString("email")
                        val role = user.optString("role", "user")

                        // simpan session
                        session!!.createLoginSession(idUser, nama, userEmail, role)

                        // tampil toast & lanjut ke activity sesuai role
                        Toast.makeText(
                            this@LoginActivity,
                            "Login berhasil. Selamat datang, " + nama,
                            Toast.LENGTH_SHORT
                        ).show()
                        gotoAppropriateActivity(role)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login gagal: " + message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this@LoginActivity, "Response parse error", Toast.LENGTH_LONG)
                        .show()
                }
            },
            Response.ErrorListener { error: VolleyError? ->
                progLogin!!.setVisibility(View.GONE)
                btnLogin!!.setEnabled(true)
                Toast.makeText(
                    this@LoginActivity,
                    "Network error: " + error!!.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): MutableMap<String?, String?> {
                val params: MutableMap<String?, String?> = HashMap<String?, String?>()
                params.put("email", email)
                params.put("password", password)
                return params
            }
        }

        queue.add<String?>(req)
    }

    private fun gotoAppropriateActivity(role: String?) {
        if ("admin".equals(role, ignoreCase = true)) {
            val i: Intent = Intent(this@LoginActivity, AdminDashboardActivity::class.java)
            startActivity(i)
        } else {
            val i: Intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    companion object {
        // sesuaikan base URL jika perlu
        private const val LOGIN_URL = "http://10.0.2.2/pluto_api/login_user.php"
    }
}