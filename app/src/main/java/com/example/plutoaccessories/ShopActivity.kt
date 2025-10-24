package com.example.plutoaccessories.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plutoaccessories.R
import com.example.plutoaccessories.adapters.ProductAdapter

class ShopActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        recyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Data dummy produk (sementara, nanti bisa diganti dari API / DB)
        productList = mutableListOf(
            Product("Gelang Emas", "Perhiasan Premium", "Rp 2.500.000", R.drawable.gelang),
            Product("Cincin Perak", "Elegan & Minimalis", "Rp 900.000", R.drawable.cincin),
            Product("Kalung Mutiara", "Cantik & Anggun", "Rp 1.800.000", R.drawable.kalung),
            Product("Jam Tangan Couple", "Klasik", "Rp 3.200.000", R.drawable.jam)
        )

        productAdapter = ProductAdapter(this, productList)
        recyclerView.adapter = productAdapter
    }
}
