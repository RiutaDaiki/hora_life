package com.example.horalife.diary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.horalife.R
import com.example.horalife.databinding.EntriesFragmentBinding
import java.time.LocalDate

class EntrieFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = EntriesFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val data = LocalDate.now()
        Log.i("", data.toString())
        val cancelBtn = binding.root.findViewById<Button>(R.id.diary_cancel_btn)
        cancelBtn.setOnClickListener(){
            showConfirm()
        }

        return binding.root
    }
    private fun showConfirm(){

    }
}