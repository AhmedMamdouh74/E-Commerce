package com.example.e_commerce.ui.features.subcategories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.SubCategories
import com.example.e_commerce.databinding.ItemSubCategoriesBinding

class SubCategoriesAdapter(private var subCategories: List<SubCategories?>?) :
    RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemSubCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bind(subCategories!![position])

        onItemClickListener?.let { clickListener ->
            holder.itemView.setOnClickListener {
                notifyItemChanged(position)
                clickListener.onItemClick(position, subCategories!![position])
            }
        }

        //  category!![position]?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int = subCategories?.size ?: 0
    fun bindCategories(categories: List<SubCategories?>) {
        this.subCategories = categories
        notifyDataSetChanged()
    }

    class ViewHolder(private val itemBinding: ItemSubCategoriesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(subCategory: SubCategories?) {
            itemBinding.subCategory=subCategory


        }
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: SubCategories?)
    }
}