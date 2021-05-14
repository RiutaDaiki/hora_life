package com.example.horalife.diary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.databinding.ItemDiaryBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DiaryViewAdapter(private val lifecycleOwner: LifecycleOwner, private val contentList: List<DiaryContent>, private val context: Context?)
    : RecyclerView.Adapter<DiaryViewAdapter.DiaryViewHolder>() {
    lateinit var bitmap: Bitmap
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
        val storageRef = Firebase.storage.reference
        val thumbnailRef = storageRef.child("horanikki-thumbnail/${contentList[position].pngFileName}")
        val ONE_MEGABYTE: Long = 1024 * 1024
        thumbnailRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            holder.binding.thumbnail.setImageBitmap(bitmap)
        }.addOnFailureListener {
            val drawable = ContextCompat.getDrawable(context!!, R.drawable.no_image)
            val bitmapDrawable = drawable as BitmapDrawable
            bitmap = bitmapDrawable.bitmap
            holder.binding.thumbnail.setImageBitmap(bitmap)
                }
        holder.binding.wrapper.setOnClickListener(){
            //日記に詳細表示画面
        }
        holder.binding.lifecycleOwner = lifecycleOwner
    }
}










