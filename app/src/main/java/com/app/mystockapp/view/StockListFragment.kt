package com.app.mystockapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mystockapp.R
import com.app.mystockapp.adapter.StockRecyclerAdapter
import com.app.mystockapp.databinding.FragmentStockListBinding
import com.app.mystockapp.model.StockRequest
import com.app.mystockapp.util.AES
import com.app.mystockapp.util.SharedPreferencesHelper
import com.app.mystockapp.util.Status
import com.app.mystockapp.viewmodel.StockViewModel
import javax.inject.Inject


class StockListFragment @Inject constructor(
    val stockRecyclerAdapter: StockRecyclerAdapter
) : Fragment(R.layout.fragment_stock_list) {


    private var fragmentBinding: FragmentStockListBinding? = null
    lateinit var viewModel: StockViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sp = SharedPreferencesHelper(requireContext())
        viewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java)


        var key = sp.getSharedPreference("Authorization", "")
        var aesIV = sp.getSharedPreference("aesIV", "")
        var aesKey = sp.getSharedPreference("aesKey", "")
        var period = AES.encrypt("all", aesKey!!, aesIV!!)

        viewModel.getStockList(key!!, StockRequest(period),aesKey,aesIV)
        val binding = FragmentStockListBinding.bind(view)
        fragmentBinding = binding

        binding.stockList.adapter = stockRecyclerAdapter
        binding.stockList.layoutManager = LinearLayoutManager(context)

        filterList()
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.stockList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {


                        stockRecyclerAdapter!!.stocks = it.data!!
                        fragmentBinding?.progressBar?.visibility = View.GONE


                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    fragmentBinding?.progressBar?.visibility = View.GONE

                }

                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE

                }
            }
        })
    }
    fun filterList() {
        fragmentBinding?.stockListSearch!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(value: String?): Boolean {
                if (value != null && value != "") {
                    viewModel.getFilterStocks(value)
                    fragmentBinding?.stockListSearch!!.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                  viewModel.getStocks()

                }
                return false

            }
        })

    }
    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}