package com.example.e_commerce.ui.home.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Product
import com.example.e_commerce.databinding.ItemWishlistBinding

class WishlistAdapter(private var products: List<Product?>?) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = products?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products?.get(position))
       // onItemClickListener?.let {clickListener->
            holder.itemBinding.wishlistFavourite.setOnClickListener{
                notifyItemChanged(position)
                onItemClickListener?.onItemClick(position,products!![position] )

            }
       // }

    }
    class ViewHolder(val itemBinding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: Product?) {
            itemBinding.product =product
            itemBinding.apply {
                Glide
                    .with(itemView)
                    .load(product?.imageCover)
                    .into(image)
            }
        }
    }

    var onItemClickListener:OnItemClickListener?=null
    fun interface OnItemClickListener{
        fun  onItemClick(position:Int,item:Product?)
    }
//    fun favouriteProductDeleted(product: Product?){
//        products?.remove(product)
//        notifyDataSetChanged()
//    }
    fun bindProducts(products: List<Product?>?){
        this.products=products
    }

}