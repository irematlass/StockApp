package com.app.mystockapp.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.mystockapp.R
import com.app.mystockapp.databinding.FragmentStockDetailBinding
import com.app.mystockapp.model.DetailRequest
import com.app.mystockapp.model.StockDetail
import com.app.mystockapp.util.AES
import com.app.mystockapp.util.SharedPreferencesHelper
import com.app.mystockapp.util.Status
import com.app.mystockapp.viewmodel.StockViewModel


class StockDetailFragment : Fragment(R.layout.fragment_stock_detail) {

    private var fragmentBinding:FragmentStockDetailBinding?=null
    private var stockId=0
    lateinit var viewModel:StockViewModel
    var aes= AES()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            stockId=StockDetailFragmentArgs.fromBundle(it).stockId
        }

        var sp= SharedPreferencesHelper(requireContext())

        val binding=FragmentStockDetailBinding.bind(view)
        fragmentBinding=binding

        viewModel= ViewModelProvider(requireActivity()).get(StockViewModel::class.java)

        var key=sp.getSharedPreference("Authorization","")
        var aesIV=sp.getSharedPreference("aesIV","")
        var aesKey=sp.getSharedPreference("aesKey","")
        var id=aes.encrypt(stockId.toString(),aesKey!!,aesIV!!)
       viewModel.getStockDetail(key!!, DetailRequest(id))
        subscribeToObservers(aesKey,aesIV)
    }
    fun subscribeToObservers(aeskey:String,aesıv:String){
        viewModel.stockdetail.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    var detail=it.data
                    if (detail?.status?.isSuccess!!) {
                        var symboltxt=aes.decrypt(detail.symbol,aeskey,aesıv)
                        fragmentBinding?.detailBid!!.text= "Alış: "+detail.bid
                        fragmentBinding?.detailCount!!.text="Adet: "+detail.count
                        fragmentBinding?.detailDifference!!.text="% Fark: "+detail.difference
                        fragmentBinding?.detailHighest!!.text= "Günlük Yüksek: "+detail.highest
                        fragmentBinding?.detailLowest!!.text="Günlük Düşük: "+detail.lowest
                        fragmentBinding?.detailMaximum!!.text="Tavan: "+detail.maximum
                        fragmentBinding?.detailMinimum!!.text="Taban: "+detail.minimum
                        fragmentBinding?.detailOffer!!.text="Satış: "+detail.offer
                        fragmentBinding?.detailPrice!!.text="Fiyat: "+detail.price
                        fragmentBinding?.detailVolume!!.text="Hacim: "+detail.volume
                        fragmentBinding?.detailSymbol!!.text="Sembol: "+symboltxt
                        if (detail.isUp) {
                            fragmentBinding?.detailChange!!.text ="Sembol:  ▲"
                            fragmentBinding?.detailChange!!.setTextColor(Color.GREEN)
                        } else if (detail.isDown) {
                            fragmentBinding?.detailChange!!.text = "Sembol: ▼"
                            fragmentBinding?.detailChange!!.setTextColor(Color.RED)
                        } else {
                            fragmentBinding?.detailChange!!.text ="Sembol: ━"
                            fragmentBinding?.detailChange!!.setTextColor(Color.YELLOW)
                        }
                        }else{
                        Toast.makeText(requireContext(),detail?.status?.error.message ?: "Error", Toast.LENGTH_LONG).show()

                    }

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()


                }


            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding=null
        super.onDestroyView()
    }


}