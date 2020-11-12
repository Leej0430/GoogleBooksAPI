package com.example.googlebooksapi

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter(): RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private lateinit var dataSet: BooksResponse

    fun setDataSet(dataSet:BooksResponse){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class BooksViewHolder(val booksView: View)
        : RecyclerView.ViewHolder(booksView){

        private val tvTitle: TextView
                = booksView.findViewById(R.id.tv_item_title)
        private val tvSubTitle: TextView
                =booksView.findViewById(R.id.tv_item_subtitle)

        fun onBind(dataItem: ItemsDescription){
            tvTitle.text = dataItem.volumeInfo.title
            tvSubTitle.text = dataItem.volumeInfo.subtitle
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(
            LayoutInflater.from(parent.context).
            inflate(R.layout.item_layout,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
         return dataSet.items.size
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
       holder.onBind(dataSet.items[position])
    }
}