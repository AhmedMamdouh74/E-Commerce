package com.example.e_commerce.ui.features.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.cart.loggedCart.ProductsItem
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemCartBinding

class CartAdapter :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(val itemBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(cartProduct: ProductsItem?) {
            itemBinding.product = cartProduct
            itemBinding.apply {
                Glide
                    .with(itemView)
                    .load(product?.product?.imageCover)
                    .placeholder(R.drawable.ic_download)
                    .error(R.drawable.ic_error)
                    .into(image)
            }

        }


    }


    private val diffUtil = object : DiffUtil.ItemCallback<ProductsItem>() {
        override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem == newItem
        }

    }
    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)
    fun bindProducts(productsItem: MutableList<ProductsItem?>?) {
        asyncListDiffer.submitList(productsItem)
    }
    fun cartProductDeleted(product: ProductsItem?) {
        val items = asyncListDiffer.currentList.toMutableList()
        items.remove(product)
        asyncListDiffer.submitList(items)
    }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = asyncListDiffer.currentList[position]
        holder.bind(data)

        holder.itemBinding.cartDelete.setOnClickListener {
            onItemClickListener?.onItemClick(position, data)

        }
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: ProductsItem?)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

}