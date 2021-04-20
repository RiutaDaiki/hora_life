package com.example.horalife.Example

import android.provider.ContactsContract
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.Sound
import com.example.horalife.databinding.ItemExampleRecyclerBinding

class RecyclerViewAdapter(private val displayData: List<Sound>): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding: ItemExampleRecyclerBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemExampleRecyclerBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount() = displayData.size


    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        val viewModel = ExampleFragmentViewModel()
        holder.binding.soundText.text = viewModel.dataList[position].soundName
    }
}

//item_recycler.xmlのTextViewのTextの記述を見る