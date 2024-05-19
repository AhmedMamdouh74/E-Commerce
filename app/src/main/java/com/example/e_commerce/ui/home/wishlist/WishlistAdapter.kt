package com.example.e_commerce.ui.home.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Product
import com.example.domain.model.cart.loggedCart.ProductsItem
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemWishlistBinding

class WishlistAdapter :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    private val wishlistDiffUtil = object : DiffUtil.ItemCallback<Product?>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
    private val wishlistAsyncListDiffer = AsyncListDiffer(this, wishlistDiffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =

            ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)

    }


    override fun getItemCount(): Int = wishlistAsyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val wishlistData = wishlistAsyncListDiffer.currentList[position]

        holder.bind(wishlistData, cart)

        // onItemClickListener?.let {clickListener->
        holder.itemBinding.wishlistFavourite.setOnClickListener {
            notifyItemChanged(position)
            onItemClickListener?.onItemClick(position, wishlistData)

        }
        // }
        holder.itemBinding.btnAddToCart.setOnClickListener {
            notifyItemChanged(position)
            onItemAddedListener?.onItemClick(position, wishlistData)
        }


    }


    private var cart: MutableList<ProductsItem?>? = null
    fun setCart(cart: MutableList<ProductsItem?>?) {
        this.cart = cart
        notifyDataSetChanged()

    }




    class ViewHolder(var itemBinding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: Product?, cart: List<ProductsItem?>?) {
            itemBinding.btnAddToCart.text = "Add To Cart"
            cart?.forEach {
                if (it?.product?.id == product?.id) {
                    itemBinding.btnAddToCart.text = "Added To Cart"
                    product?.addedToCart = true

                }
            }

            itemBinding.product = product
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

    var onItemAddedListener: OnItemClickListener? = null

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: Product?)
    }

    fun favouriteProductDeleted(product: Product?) {
        val data = wishlistAsyncListDiffer.currentList.toMutableList()
        data.remove(product)
        wishlistAsyncListDiffer.submitList(data)
    }

    fun bindProducts(product: MutableList<Product?>?) {
        wishlistAsyncListDiffer.submitList(product)
    }

}