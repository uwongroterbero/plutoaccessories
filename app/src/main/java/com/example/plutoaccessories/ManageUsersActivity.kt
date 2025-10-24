package com.example.plutoaccessories.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.plutoaccessories.R
import com.example.plutoaccessories.adapters.ManageUsersAdapter
import com.example.plutoaccessories.models.UserModel
import org.json.JSONArray
import org.json.JSONException

class ManageUsersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ManageUsersAdapter
    private val userList = mutableListOf<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_users)

        recyclerView = findViewById(R.id.recyclerUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ManageUsersAdapter(userList)
        recyclerView.adapter = adapter

        loadUsers()
    }

    private fun loadUsers() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/pluto_api/get_users.php"

        val request = StringRequest(Request.Method.GET, url, { response ->
            try {
                val jsonArray = JSONArray(response)
                userList.clear()

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val user = UserModel(
                        obj.getInt("id_user"),
                        obj.getString("nama"),
                        obj.getString("email"),
                        obj.getString("alamat"),
                        obj.getString("no_hp"),
                        obj.getString("role")
                    )
                    userList.add(user)
                }

                adapter.notifyDataSetChanged()
            } catch (e: JSONException) {
                Toast.makeText(this, "Error parsing JSON!", Toast.LENGTH_SHORT).show()
            }
        }, {
            Toast.makeText(this, "Gagal memuat data pengguna", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)
    }
}
