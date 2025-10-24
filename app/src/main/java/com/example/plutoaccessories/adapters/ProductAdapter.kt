package com.example.plutoaccessories.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plutoaccessories.R
import com.example.plutoaccessories.models.Product

class ProductAdapter(private val context: Context, private val productList: List<Product>) :
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
    val tvNama: TextView = view.findViewById(R.id.tvNamaProduct)
    val tvHarga: TextView = view.findViewById(R.id.tvHargaProduct)
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
    return ProductViewHolder(view)
}

override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    val product = productList[position]
    holder.imgProduct.setImageResource(product.gambar)
    holder.tvNama.text = product.nama
    holder.tvHarga.text = product.harga
}

override fun getItemCount(): Int = productList.size
}
