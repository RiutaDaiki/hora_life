package com.example.horalife.example

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.Sound
import com.example.horalife.databinding.ItemExampleRecyclerBinding

class RecyclerViewAdapter(private val displayData: List<Sound>, private  val lifecycleOwner: LifecycleOwner, private val context: Context?): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding: ItemExampleRecyclerBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemExampleRecyclerBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = displayData.size

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.binding.sound = displayData[position]
        val exampleFragment = ExampleFragment()
        holder.binding.soundArea.setOnClickListener {
            context?.let {
                exampleFragment.playMedia(displayData[position].soundFile, it)
            }
        }
        holder.binding.lifecycleOwner = lifecycleOwner
    }
}

//item_recycler.xmlのTextViewのTextの記述を見る