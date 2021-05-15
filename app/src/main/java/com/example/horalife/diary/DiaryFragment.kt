package com.example.horalife.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.R
import com.example.horalife.databinding.DiaryFragmentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DiaryFragment: Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        binding.view = this
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        binding.lifecycleOwner = viewLifecycleOwner

            val db = Firebase.firestore
            db.collection("Diary items")
                    .get()
                    .addOnSuccessListener { result ->
                        var contentList = mutableListOf<DiaryContent>()

                        for (document in result) {
                            val d = document.data
                            val content = DiaryContent(d["recordedDate"].toString(), d["comment"].toString(), d["pngFileName"].toString())
                            contentList.add(content)
                        }
                        adapter = DiaryViewAdapter(viewLifecycleOwner, contentList, this.requireContext())
                        binding.diaryRecycler.adapter = adapter
                    }

        return binding.root
    }
     fun showEntries(){
        val entries = EntrieFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, entries)
        fragmentTransaction.commit()
    }
}