package com.burakkirgil.leblonapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.burakkirgil.leblonapp.databinding.RecyclerRowBinding

class CartAdapter(val productList : ArrayList<Product>) : RecyclerView.Adapter<CartAdapter.CartHolder>() {
    class CartHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.binding.recyclerViewProductName.text = productList[position].getPId()
        holder.binding.recyclerViewQuantity.text = productList[position].getQuantity().toString()

        holder.binding.recyclerViewIncrementButton.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct(productList[position].getPId(), 1)
            holder.binding.recyclerViewQuantity.text =
                productList[position].getQuantity().toString()
        }

        holder.binding.recyclerViewDecrementButton.setOnClickListener {
            CurrentUser.user?.getCart()?.decreaseQuantity(productList[position].getPId())
            if (productList[position].getQuantity() > 0) {
                holder.binding.recyclerViewQuantity.text =
                    productList[position].getQuantity().toString()
            } else {
                productList.remove(productList[position])
                notifyDataSetChanged()
            }
        }

    }
}


