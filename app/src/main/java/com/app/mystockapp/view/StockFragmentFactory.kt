package com.app.mystockapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.app.mystockapp.adapter.StockRecyclerAdapter
import javax.inject.Inject

class StockFragmentFactory @Inject constructor(
    val stockRecyclerAdapter: StockRecyclerAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            StockListFragment::class.java.name -> StockListFragment(stockRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}