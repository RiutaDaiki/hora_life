package com.example.horalife.diary

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.databinding.ItemDiaryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DiaryViewAdapter(private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<DiaryViewAdapter.DiaryViewHolder>() {
    lateinit var date: String
    inner class DiaryViewHolder(val binding: ItemDiaryBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemDiaryBinding.inflate(inflater, parent, false)
        return DiaryViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val db = Firebase.firestore

        db.collection("Diary items")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    date = document["dateTime"].toString()
                    Log.d("いいいいいいいいいいいいいいいい", date)
                }
            }
        holder.binding.lifecycleOwner = lifecycleOwner
    }
}