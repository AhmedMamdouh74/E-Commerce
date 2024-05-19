package com.example.e_commerce.ui.home.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Category
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemCategoryBinding

class CategoriesAdapter(private var category: List<Category?>?) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    private var selectedItemPosition = 0
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            category!![position], selectedItemPosition ==position

        )

        onItemClickListener?.let {clickListener->
            holder.itemView.setOnClickListener{
                notifyItemChanged(selectedItemPosition)
                selectedItemPosition=position
                notifyItemChanged(position)
                clickListener.onItemClick(position,category!![position])
            }
        }

        //  category!![position]?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int = category?.size ?: 0
    fun bindCategories(categories: List<Category?>) {
        this.category = categories
        notifyDataSetChanged()
    }

    class ViewHolder(private val itemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: Category?, selected: Boolean) {
            itemBinding.category = category
            if (selected) {
                itemBinding.selectionBar.visibility = View.VISIBLE
                itemBinding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemBinding.root.context,
                        R.color.white
                    )
                )
            } else {
                itemBinding.selectionBar.visibility = View.INVISIBLE
                itemBinding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemBinding.root.context,
                        R.color.transparent
                    )
                )

            }
            itemBinding.executePendingBindings()

        }
    }


    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: Category?)
    }
}