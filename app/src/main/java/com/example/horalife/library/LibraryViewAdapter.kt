package com.example.horalife.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.databinding.ItemLibraryBinding

class LibraryViewAdapter(private val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<LibraryViewAdapter.LibraryViewHolder>(){

    inner class  LibraryViewHolder(val binding: ItemLibraryBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemLibraryBinding.inflate(inflater, parent, false)
        return LibraryViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
    }

}