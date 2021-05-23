package com.example.horalife.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horalife.R
import com.example.horalife.databinding.DiaryFragmentBinding

class DiaryFragment : Fragment() {
    private lateinit var adapter: DiaryViewAdapter
    private lateinit var binding: DiaryFragmentBinding
<<<<<<< HEAD
    private val viewModel: DiaryViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)
=======
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiaryFragmentBinding.inflate(layoutInflater, container, false)

        // TODO: これいらない気がする
        binding.view = this

>>>>>>> 673b9c3e8e0cef065081476ea11ff83aa809d226
        binding.diaryRecycler.layoutManager = LinearLayoutManager(context)
        binding.lifecycleOwner = viewLifecycleOwner
        //↓日記画面のfabはこのクラスのshowEntries()を起動します。diary_fragment.xmlでandroid:onClick="@{() -> view.showEntries()}"としているので、この記述は必要だと思います。
        binding.view = this

<<<<<<< HEAD
        adapter = DiaryViewAdapter(viewLifecycleOwner, viewModel, this.requireContext())
=======

        // TODO: コメントアウト消す

//                    val db = Firebase.firestore
//            db.collection("Diary items")
//                    .get()
//                    .addOnSuccessListener { result ->
//                        var contentList = mutableListOf<DiaryContent>()
//
//                        for (document in result) {
//                            val d = document.data
//                            val content = DiaryContent(d["recordedDate"].toString(), d["comment"].toString(), d["pngFileName"].toString())
//                            contentList.add(content)
//                        }
//                        adapter = DiaryViewAdapter(viewLifecycleOwner, contentList, this.requireContext())
//                        binding.diaryRecycler.adapter = adapter
//                    }

        // TODO: ViewModelにおく(val diaryList = MutableLiveData<List<DiaryContent>>)
        // TODO: diaryList.observeしてObserverの中で${adapter.notifyDataSetChanged()}する
        val diaryList = mutableListOf<DiaryContent>()
        adapter = DiaryViewAdapter(viewLifecycleOwner, diaryList, this.requireContext())
        DiaryRepository().getDiaryInfo {
            diaryList.addAll(it)
            adapter.notifyDataSetChanged()
        }
//        adapter = DiaryViewAdapter(viewLifecycleOwner, lam, this.requireContext())
>>>>>>> 673b9c3e8e0cef065081476ea11ff83aa809d226
        binding.diaryRecycler.adapter = adapter

        viewModel.diaryList.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
            DiaryViewAdapter(viewLifecycleOwner, viewModel, requireContext())
        }

        viewModel.isRowClicked.observe(viewLifecycleOwner){
            val action = DiaryFragmentDirections.actionDiaryToDiaryDetail(it)
            findNavController().navigate(action)
        }
        return binding.root
    }

<<<<<<< HEAD
     fun showEntries(){
        findNavController().navigate(R.id.action_nav_diary_to_entriesFragment)
     }
=======
    fun showEntries() {
        val entries = EntrieFragment()
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, entries)
        fragmentTransaction.commit()
    }

    fun getDiaryInfo(list: MutableList<DiaryContent>) {

        val db = Firebase.firestore
        db.collection("Diary items")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val d = document.data
                    val content = DiaryContent(
                        d["recordedDate"].toString(),
                        d["comment"].toString(),
                        d["pngFileName"].toString()
                    )
                    list.add(content)
                }
            }
    }
>>>>>>> 673b9c3e8e0cef065081476ea11ff83aa809d226

}
