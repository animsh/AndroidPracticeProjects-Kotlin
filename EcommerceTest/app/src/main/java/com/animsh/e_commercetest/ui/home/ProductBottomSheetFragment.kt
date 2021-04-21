package com.animsh.e_commercetest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animsh.e_commercetest.databinding.BottomSheetProductBinding
import com.animsh.e_commercetest.entities.Product
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ProductBottomSheetFragment(
    private val product: Product
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetProductBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "ProductBottomDialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetProductBinding.inflate(inflater, container, false)
        binding.product = product
        return binding.root
    }

    fun newInstance(): ProductBottomSheetFragment {
        return ProductBottomSheetFragment(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
