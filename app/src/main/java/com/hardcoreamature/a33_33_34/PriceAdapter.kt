package com.hardcoreamature.a33_33_34

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PriceAdapter(private val prices: List<String>) : RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    class PriceViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return PriceViewHolder(textView)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        holder.textView.text = prices[position]
    }

    override fun getItemCount() = prices.size
}
