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
    private var wishlist: List<Product?>? = null
    fun setWishlist(wishlist: List<Product?>?) {
        this.wishlist = wishlist
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product!![position], wishlist)
        //  onItemClickListener.let {
        holder.apply {
            itemView.setOnClickListener {
                notifyItemChanged(position)
                onItemClickListener?.onItemClick(position, product!![position])
            }
            itemBinding.addToFavourites.setOnClickListener {
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
        fun bind(product: Product?, wishlist: List<Product?>?) {
            itemBinding.product = product
            itemBinding.addToFavourites.setImageResource(R.drawable.add_favourite)
            product?.isAdded=false
            wishlist?.forEach {
                if (it?.id == product?.id) {
                    itemBinding.addToFavourites.setImageResource(R.drawable.active_heart)
                    product?.isAdded = true
                }

            }

            itemBinding.apply {
                Glide
                    .with(itemView)
                    .load(product?.imageCover)
                    .placeholder(R.drawable.ic_download)
                    .error(R.drawable.ic_error)
                    .into(image)

            }
        }
    }
}