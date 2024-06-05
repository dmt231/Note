package com.example.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.note.databinding.LayoutDetailFragmentBinding
import com.example.note.room.Note
import com.example.note.room.NoteDatabase

class DetailFragment : Fragment() {
    private lateinit var viewBinding : LayoutDetailFragmentBinding
    private lateinit var noteDatabase : NoteDatabase
    private var note : Note? = null
    private var type : String = "Create"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailFragmentBinding.inflate(inflater, container, false)
        noteDatabase = NoteDatabase.getInstance(requireContext())
        viewBinding.done.setOnClickListener {
            done()
        }
        viewBinding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewBinding.detailFragmentLayout.setOnClickListener{
            //DO NOTHING
        }
        getData()
        return viewBinding.root
    }
    private fun done() {
        when(type){
            "Create" ->{
                if(viewBinding.editContent.text.toString().isNotEmpty() || viewBinding.editTitle.text.toString().isNotEmpty()){
                    val note = Note(viewBinding.editTitle.text.toString(), viewBinding.editContent.text.toString())
                    noteDatabase.noteDao().insertNote(note)
                    Toast.makeText(requireContext(), "Insert Success", Toast.LENGTH_SHORT).show()
                }
            }
            "Edit" ->{
                if(viewBinding.editContent.text.toString().isNotEmpty() || viewBinding.editTitle.text.toString().isNotEmpty()){
                    note!!.setTitle(viewBinding.editTitle.text.toString())
                    note!!.setContent(viewBinding.editContent.text.toString())
                    noteDatabase.noteDao().updateNote(note!!)
                    Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getData(){
        val bundle = arguments
        if(bundle != null){
            type = "Edit"
            this.note = bundle["Note"] as Note
            viewBinding.editTitle.setText(note!!.getTitle())
            viewBinding.editContent.setText(note!!.getContent())
        }
    }
}