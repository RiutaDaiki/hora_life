package com.example.horalife.recycler

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.horalife.R
import com.example.horalife.entity.DiaryContent
import com.example.horalife.viewModel.DiaryViewModel
import com.example.horalife.databinding.ItemDiaryBinding
import java.sql.Timestamp


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
        val comment = viewModel.diaryList.value?.get(position)!!.comment
        val rowComment = if (comment.length < 40) comment else comment.substring(0..41)

        val content = DiaryContent(
            viewModel.diaryList.value?.get(position)!!.recordedDate,
            rowComment,
            viewModel.diaryList.value?.get(position)!!.pngFileName,
            Timestamp(System.currentTimeMillis()),
            viewModel.diaryList.value?.get(position)!!.videoFileName,
            viewModel.diaryList.value?.get(position)!!.videoPath
        )
        holder.binding.content = content
        holder.binding.wrapper.setOnClickListener {
            onClickRow(position)
        }

        viewModel.getBitMap(position) {
            holder.binding.thumbnail.setImageBitmap(it ?: createNoImage())
        }
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    fun createNoImage(): Bitmap {
        val drawable = ContextCompat.getDrawable(context, R.drawable.no_image)
        val bitmapDrawable = drawable as BitmapDrawable
        return bitmapDrawable.bitmap
    }
}
