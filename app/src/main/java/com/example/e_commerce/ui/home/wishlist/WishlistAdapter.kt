package com.example.e_commerce.ui.home.wishlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Product
import com.example.domain.model.cart.loggedCart.ProductsItem
import com.example.e_commerce.databinding.ItemWishlistBinding

class WishlistAdapter(private var products: MutableList<Product?>?) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =

            ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("TAG", "onCreateViewHolder:$itemBinding ")
        return ViewHolder(itemBinding)

    }

    override fun getItemCount(): Int = products?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder:$products ")
        holder.bind(products?.get(position),cart)

        // onItemClickListener?.let {clickListener->
        holder.itemBinding.wishlistFavourite.setOnClickListener {
            notifyItemChanged(position)
            onItemClickListener?.onItemClick(position, products!![position])

        }
        // }
        holder.itemBinding.btnAddToCart.setOnClickListener{
            notifyItemChanged(position)
            onItemAddedListener?.onItemClick(position,products!![position])
        }


    }
    private var cart: MutableList<ProductsItem?>? = null
    fun setCart(cart: MutableList<ProductsItem?>?) {
        this.cart = cart
        notifyDataSetChanged()

    }

    class ViewHolder(var itemBinding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: Product?,cart: List<ProductsItem?>?) {
            Log.d("TAG", "bind:$product ")
            itemBinding.btnAddToCart.text="Add To Cart"
            product?.addedToCart=false
            cart?.forEach{
                if (it?.product?.id==product?.id){
                    itemBinding.btnAddToCart.text="Added To Cart"
                    product?.addedToCart=true

                }
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
    var onItemAddedListener:OnItemClickListener?=null

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: Product?)
    }

    fun favouriteProductDeleted(product: Product?) {
        products?.remove(product)
        notifyDataSetChanged()
    }

    fun bindProducts(product: MutableList<Product?>?) {
        this.products = product
        notifyDataSetChanged()
        Log.d("TAG", "bindProductsAdapter:$product ")
    }

}