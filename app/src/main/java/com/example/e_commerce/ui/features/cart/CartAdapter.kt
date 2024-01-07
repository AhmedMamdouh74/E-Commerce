package com.example.e_commerce.ui.features.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.cart.loggedCart.ProductsItem
import com.example.e_commerce.databinding.ItemCartBinding

class CartAdapter(private var product: MutableList<ProductsItem?>?) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder( val itemBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(cart: ProductsItem?) {
            itemBinding.product = cart?.product
            itemBinding.apply {
                Glide
                    .with(itemView)
                    .load(product?.imageCover)
                    .into(image)
            }

        }

    }
    fun bindProducts(product: MutableList<ProductsItem?>?){
        this.product=product
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product!![position])
        holder.itemBinding.cartDelete.setOnClickListener {
            onItemClickListener?.onItemClick(position,product!![position])
        }
    }
    var onItemClickListener:OnItemClickListener?=null
    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: ProductsItem?)
    }

    override fun getItemCount(): Int = product?.size ?: 0


}