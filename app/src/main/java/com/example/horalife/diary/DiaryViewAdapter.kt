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
        var dc : DiaryContent? = null
            db.collection("Diary items")
                    .get()
                    .addOnSuccessListener { result ->
                        for(document in result){
                            val d =  document.data

                            holder.binding.content = DiaryContent(d["dateTime"].toString(), d["comment"].toString())

                            println(d["dateTime"])
                        }
                    }

        holder.binding.lifecycleOwner = lifecycleOwner
    }
}