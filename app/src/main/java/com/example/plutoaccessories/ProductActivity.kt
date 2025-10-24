package com.example.plutoaccessories.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plutoaccessories.R
import com.example.plutoaccessories.adapters.ProductAdapter
import com.example.plutoaccessories.models.Product

class ProductActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        recyclerView = findViewById(R.id.recyclerViewProducts)

        // Dummy data produk
        productList.add(Product("Cincin Silver", "Cincin perak elegan untuk sehari-hari", "Rp 75.000", R.drawable.cincinperak))
        productList.add(Product("Kalung Titanium", "Kalung kuat dan tahan karat", "Rp 95.000", R.drawable.kalungtitanium))
        productList.add(Product("Gelang Kulit", "Gelang kulit hitam keren", "Rp 60.000", R.drawable.gelangkulit))
        productList.add(Product("Anting Mutiara", "Anting manis cocok untuk acara resmi", "Rp 80.000", R.drawable.antingmutiara))

        productAdapter = ProductAdapter(productList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter
    }
}
