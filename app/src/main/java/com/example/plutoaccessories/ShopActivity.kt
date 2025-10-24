package com.example.plutoaccessories.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plutoaccessories.R
import com.example.plutoaccessories.adapters.ProductAdapter
import com.example.plutoaccessories.models.Product

class ShopActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        recyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Data dummy produk (sementara)
        productList = mutableListOf(
            Product("Gelang Emas", "Perhiasan Premium", "Rp 2.500.000", R.drawable.gelangemas),
            Product("Cincin Perak", "Elegan & Minimalis", "Rp 900.000", R.drawable.cincinperak),
            Product("Kalung Mutiara", "Cantik & Anggun", "Rp 1.800.000", R.drawable.kalungmutiara),
            Product("Jam Tangan Couple", "Klasik & Serasi", "Rp 3.200.000", R.drawable.jamtanganklasik)
        )

        productAdapter = ProductAdapter(productList)
        recyclerView.adapter = productAdapter
    }
}
