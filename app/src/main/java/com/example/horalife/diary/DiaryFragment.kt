package com.example.horalife.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.R
import com.example.horalife.databinding.DiaryFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DiaryFragment: Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
        val fab = binding.root.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(){
            showEntries()
        }

        val db = Firebase.firestore
        db.collection("Diary items")
                .get()
                .addOnSuccessListener { result ->
                    var contentList = mutableListOf<DiaryContent>()

                    for(document in result){
                        val d =  document.data
                        val addContent = DiaryContent(d["dateTime"].toString(), d["comment"].toString(), d["thumbnail"] as ByteArray)
                        contentList.add(addContent)
                    }
                    adapter = DiaryViewAdapter(viewLifecycleOwner, contentList)
                    binding.diaryRecycler.adapter = adapter
                }
        
        binding.lifecycleOwner = viewLifecycleOwner
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
    private fun showEntries(){
        val entries = EntrieFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, entries)
        fragmentTransaction.commit()
    }
}