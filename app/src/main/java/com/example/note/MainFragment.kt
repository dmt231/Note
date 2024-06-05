package com.example.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.adapter.RecyclerViewAdapter
import com.example.note.databinding.LayoutMainFragmentBinding
import com.example.note.room.Note
import com.example.note.viewModel.NoteViewModel

class MainFragment : Fragment(){
    private lateinit var viewBinding : LayoutMainFragmentBinding
    private lateinit var noteViewModel : NoteViewModel
    private lateinit var adapter: RecyclerViewAdapter
    private var listNote : ArrayList<Note>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutMainFragmentBinding.inflate(inflater, container, false)
        viewBinding.add.setOnClickListener {
            onChangeToAdd()
        }
        listNote = ArrayList()
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        setUpRecyclerView()
        getData()
        return viewBinding.root
    }

    private fun onChangeToAdd() {
        val detailFragment = DetailFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.mainActivity, detailFragment)
        fragmentTrans.addToBackStack(detailFragment.tag)
        fragmentTrans.commit()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getData(){
        noteViewModel.getLiveDataNote(requireContext()).observe(viewLifecycleOwner){
            if(listNote!!.isNotEmpty()){
                listNote!!.clear()
                for(note in it){
                    listNote!!.add(note)
                }
            }
            else{
                for(note in it){
                    listNote!!.add(note)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }


    private fun setUpRecyclerView(){
        viewBinding.recyclerView.setHasFixedSize(false)
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = RecyclerViewAdapter(listNote!!, object : RecyclerViewAdapter.OnClickListener{
            override fun onClickListener(note: Note) {
                changeToDetailNote(note)
            }
        })
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView.adapter = adapter
    }

    private fun changeToDetailNote(note : Note) {
        val detailFragment = DetailFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("Note", note)
        detailFragment.arguments = bundle
        fragmentTrans.add(R.id.mainActivity, detailFragment)
        fragmentTrans.addToBackStack(detailFragment.tag)
        fragmentTrans.commit()
    }
}