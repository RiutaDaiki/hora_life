package com.example.horalife.Example

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.databinding.ItemExampleRecyclerBinding

class RecyclerViewAdapter(private val data: List<DataModel>): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding: ItemExampleRecyclerBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemExampleRecyclerBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }
}

//item_recycler.xmlのTextViewのTextの記述を見る