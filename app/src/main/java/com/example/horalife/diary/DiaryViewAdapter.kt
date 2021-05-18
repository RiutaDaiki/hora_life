package com.example.horalife.diary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.databinding.ItemDiaryBinding
import kotlin.properties.Delegates

class DiaryViewAdapter(private val lifecycleOwner: LifecycleOwner,
                       private val viewModel: DiaryViewModel,
                       private val context: Context)
    : RecyclerView.Adapter<DiaryViewAdapter.DiaryViewHolder>() {
    lateinit var refString: String
    lateinit var bitmap: Bitmap

    inner class DiaryViewHolder(val binding: ItemDiaryBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemDiaryBinding.inflate(inflater, parent, false)
        return DiaryViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = viewModel.diaryList.value?.size ?: 0
    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.binding.content = viewModel.diaryList.value?.get(position)
        holder.binding.viewmodel = DiaryViewModel()
        holder.binding.wrapper.setOnClickListener {
            viewModel.onClickRow()
        }

        viewModel.diaryBitMap(position, {
            holder.binding.thumbnail.setImageBitmap(it ?: createNoImage())
        })
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    private fun createNoImage(): Bitmap{
        val drawable = ContextCompat.getDrawable(context, R.drawable.no_image)
        val bitmapDrawable = drawable as BitmapDrawable
        return bitmapDrawable.bitmap
    }
}










