package com.example.horalife.diary

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.databinding.ItemDiaryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class DiaryViewAdapter(private val lifecycleOwner: LifecycleOwner, private val contentList: List<DiaryContent>)
    : RecyclerView.Adapter<DiaryViewAdapter.DiaryViewHolder>() {

    inner class DiaryViewHolder(val binding: ItemDiaryBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemDiaryBinding.inflate(inflater, parent, false)
        return DiaryViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.binding.content = contentList[position]

        holder.binding.lifecycleOwner = lifecycleOwner
    }


}










