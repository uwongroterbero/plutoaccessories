package com.example.plutoaccessories.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plutoaccessories.R
import com.example.plutoaccessories.adapters.CartAdapter
import com.example.plutoaccessories.models.CartItem

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotalHarga: TextView
    private lateinit var btnCheckout: Button

    private lateinit var cartAdapter: CartAdapter
    private val cartList = mutableListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerViewCart)
        tvTotalHarga = findViewById(R.id.tvTotalHarga)
        btnCheckout = findViewById(R.id.btnCheckout)

        // Dummy data untuk testing
        cartList.add(CartItem("Gelang Titanium", 50000, 1, R.drawable.gelangtitanium))
        cartList.add(CartItem("Kalung Perak", 75000, 2, R.drawable.kalungtitanium))
        cartList.add(CartItem("Cincin Couple", 120000, 1, R.drawable.cincincouple))

        cartAdapter = CartAdapter(cartList) { item ->
            removeFromCart(item)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        updateTotalHarga()

        btnCheckout.setOnClickListener {
            // Simulasikan checkout
            tvTotalHarga.text = "Pesanan Diproses ✅"
            cartList.clear()
            cartAdapter.notifyDataSetChanged()
        }
    }

    private fun removeFromCart(item: CartItem) {
        cartList.remove(item)
        cartAdapter.notifyDataSetChanged()
        updateTotalHarga()
    }

    private fun updateTotalHarga() {
        val total = cartList.sumOf { it.harga * it.jumlah }
        tvTotalHarga.text = "Total: Rp $total"
    }
}
