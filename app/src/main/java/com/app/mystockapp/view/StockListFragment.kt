package com.app.mystockapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.mystockapp.R
import com.app.mystockapp.databinding.FragmentStockListBinding


class StockListFragment : Fragment(R.layout.fragment_stock_list) {

    private var fragmentBinding:FragmentStockListBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentStockListBinding.bind(view)
        fragmentBinding=binding
    }


}