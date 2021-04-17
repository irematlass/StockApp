package com.app.mystockapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.mystockapp.R
import com.app.mystockapp.model.Stock
import com.app.mystockapp.view.StockListFragmentDirections
import kotlinx.android.synthetic.main.stock_item.view.*
import javax.inject.Inject

class StockRecyclerAdapter @Inject constructor(): RecyclerView.Adapter<StockRecyclerAdapter.StockViewHolder>() {
    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val diffUtil = object : DiffUtil.ItemCallback<Stock>() {
        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem == newItem
        }
}

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var stocks: List<Stock>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        return StockViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val symbolText = holder.itemView.findViewById<TextView>(R.id.symbol_txt)
        val priceText=holder.itemView.findViewById<TextView>(R.id.price_txt)
        val differenceText = holder.itemView.findViewById<TextView>(R.id.difference_txt)
        val volumeText=holder.itemView.findViewById<TextView>(R.id.volume_txt)
        val bidText = holder.itemView.findViewById<TextView>(R.id.bid_txt)
        val offerText=holder.itemView.findViewById<TextView>(R.id.offer_txt)
        val changeText = holder.itemView.findViewById<TextView>(R.id.change_txt)
        val stock=stocks[position]
        holder.itemView.apply {
            symbolText.text=stock.symbol
            priceText.text=stock.price.toString()
            differenceText.text=stock.difference.toString()
            volumeText.text=stock.volume.toString()
            bidText.text=stock.bid.toString()
            offerText.text=stock.offer.toString()
            if (stock.isUp) {
                changeText.text = "▲"
                changeText.setTextColor(Color.GREEN)
            } else if (stock.isDown) {
                changeText.text = "▼"
                changeText.setTextColor(Color.RED)
            } else {
                changeText.text = "━"
                changeText.setTextColor(Color.YELLOW)
            }

        }
        holder.itemView.setOnClickListener {
            val action=StockListFragmentDirections.actionStockListFragmentToStockDetailFragment(stock.uid)
            Navigation.findNavController(it).navigate(action)
        }

    }
}