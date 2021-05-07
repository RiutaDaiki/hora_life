package com.example.horalife.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.databinding.ItemDiaryBinding

class DiaryViewAdapter(private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<DiaryViewAdapter.DiaryViewHolder>() {
    inner class DiaryViewHolder(val binding: ItemDiaryBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemDiaryBinding.inflate(inflater, parent, false)
        return DiaryViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: DiaryViewAdapter.DiaryViewHolder, position: Int) {

        holder.binding.lifecycleOwner = lifecycleOwner
    }
}