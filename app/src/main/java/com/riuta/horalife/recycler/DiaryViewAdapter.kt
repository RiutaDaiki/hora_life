package com.riuta.horalife.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.riuta.horalife.R
import com.riuta.horalife.viewModel.DiaryViewModel
import com.riuta.horalife.databinding.ItemDiaryBinding


class DiaryViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: DiaryViewModel,
    private val context: Context,
    private val onClickRow: (Int) -> Unit
) : RecyclerView.Adapter<DiaryViewAdapter.DiaryViewHolder>() {
    private lateinit var binding: ItemDiaryBinding

    inner class DiaryViewHolder(val binding: ItemDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        binding = ItemDiaryBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return DiaryViewHolder(binding)
    }

    override fun getItemCount(): Int = viewModel.diaryList.value?.size ?: 0

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        binding.viewmodel = viewModel
        binding.position = position
        viewModel.setList {  }
        holder.binding.wrapper.setOnClickListener {
            onClickRow(position)
        }

        viewModel.getBitMap(position) {
            holder.binding.thumbnail.setImageBitmap(it ?: createNoImage())
        }
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun createNoImage(): Bitmap {
        val drawable = context.getDrawable(R.drawable.no_image)
        val bitmapDrawable = drawable as BitmapDrawable
        return bitmapDrawable.bitmap
    }
}
