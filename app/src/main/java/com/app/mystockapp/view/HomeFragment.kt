package com.app.mystockapp.view

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.app.mystockapp.R
import com.app.mystockapp.databinding.FragmentHomeBinding
import com.app.mystockapp.databinding.FragmentStockDetailBinding
import com.app.mystockapp.model.HandshakeRequest
import com.app.mystockapp.util.SharedPreferencesHelper
import com.app.mystockapp.viewmodel.StockViewModel
import java.io.Console


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var fragmentBinding: FragmentHomeBinding? = null
    lateinit var viewModel: StockViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java)

        val binding = FragmentHomeBinding.bind(view)
        fragmentBinding = binding

        var sp = SharedPreferencesHelper(requireContext())

        viewModel.hanshakeRequest(
            HandshakeRequest(
                deviceId = android.os.Build.DEVICE,
                deviceModel = android.os.Build.MODEL,
                manifacturer = android.os.Build.MANUFACTURER,
                platformName = "Android",
                systemVersion = android.os.Build.VERSION.SDK_INT.toString()
            )
        )

        binding.homeBtn.setOnClickListener {
            var autKey = sp.getSharedPreference("Authorization", "")
            Log.d("auth", autKey.toString())
            if (autKey != null) {
                val action = HomeFragmentDirections.actionHomeFragmentToStockListFragment("all")
                Navigation.findNavController(it).navigate(action)
            }

        }
        viewModel.handshakeResponse.observe(viewLifecycleOwner, Observer {
            var response = it
            if (it.data?.status?.isSuccess!!) {
                sp.setSharedPreference("Authorization", it.data.authorization)
                sp.setSharedPreference("aesIV", it.data.aesIV)
                Log.d("authIV", it.data.aesIV.toString())
                sp.setSharedPreference("aesKey", it.data.aesKey)
                Log.d("authKey", it.data.aesKey.toString())
            } else {
                Log.d("error", it.message.toString())
            }

        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}