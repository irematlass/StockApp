package com.app.mystockapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.app.mystockapp.R
import com.app.mystockapp.databinding.FragmentStockDetailBinding


class StockDetailFragment : Fragment(R.layout.fragment_stock_detail) {

    private var fragmentBinding:FragmentStockDetailBinding?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentStockDetailBinding.bind(view)
        fragmentBinding=binding



    }

    override fun onDestroyView() {
        fragmentBinding=null
        super.onDestroyView()
    }


}