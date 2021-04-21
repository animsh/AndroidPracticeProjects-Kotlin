package com.animsh.e_commercetest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.e_commercetest.databinding.LayoutProductContainerBinding
import com.animsh.e_commercetest.entities.Product

/**
 * Created by animsh on 4/21/2021.
 */

class ProductsAdapter(
    private val activity: AppCompatActivity
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var products = emptyList<Product>()

    class ProductsViewHolder(private val binding: LayoutProductContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutProductContainerBinding.inflate(layoutInflater, parent, false)
                return ProductsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        return ProductsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            val openBottomSheet: ProductBottomSheetFragment =
                ProductBottomSheetFragment(product).newInstance()
            openBottomSheet.show(
                (activity).supportFragmentManager,
                ProductBottomSheetFragment.TAG
            )
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: List<Product>) {
        val recipesDiffUtil = ProductDiffUtil(products, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        products = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}