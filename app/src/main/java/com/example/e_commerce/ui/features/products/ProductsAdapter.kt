package com.example.e_commerce.ui.features.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Product
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemProductBinding

class ProductsAdapter(private var product: List<Product?>?) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product!![position])
        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                notifyItemChanged(position)
                onItemClickListener?.onItemClick(position, product!![position])
            }
        }

        onIconWishlistClickListener.let {
            holder.itemBinding.addToFavourites.setOnClickListener {

                onIconWishlistClickListener?.onItemClick(position, product!![position])
                notifyItemChanged(position)
            }
        }

    }

    fun bindProducts(product: List<Product?>) {
        this.product = product
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = product?.size ?: 0

    var onItemClickListener: OnItemClickListener? = null
    var onIconWishlistClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: Product?)
    }


    class ViewHolder(val itemBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: Product?) {

            if (IsAddedToFavourite.isAdded == true) {
                itemBinding.addToFavourites.setImageResource(R.drawable.active_heart)
            } else {
                itemBinding.addToFavourites.setImageResource(R.drawable.add_favourite)
            }
            itemBinding.product = product
            itemBinding.apply {
                Glide
                    .with(itemView)
                    .load(product?.imageCover)
                    .into(image)

            }
        }
    }
}