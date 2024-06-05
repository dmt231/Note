package com.example.note.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import com.example.note.databinding.LayoutRecyclerBinding
import com.example.note.room.Note
import com.example.note.room.NoteDatabase

class RecyclerViewAdapter(listNote : ArrayList<Note>, onClickListener: OnClickListener) : RecyclerView.Adapter<ViewHolder>() {
    private var listNote: ArrayList<Note>
    private var onClickListener : OnClickListener
    init {
        this.listNote = listNote
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = LayoutRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = listNote[position]
        holder.bindData(model)
        holder.getViewBinding().itemLayout.setOnClickListener {
            onClickListener.onClickListener(model)
        }
        holder.getViewBinding().itemLayout.setOnLongClickListener { view ->
            val dialogBuilder = AlertDialog.Builder(view?.context)
            dialogBuilder.setMessage("Do you want to delete this note ?")
                .setTitle("Confirm")
                .setPositiveButton(
                    "Yes"
                ) { p0, p1 ->
                    NoteDatabase.getInstance(view?.context).noteDao().delete(model)
                    listNote.remove(model)
                    notifyDataSetChanged()
                }
                .setNegativeButton("No") { p0, p1 ->
                    p0.cancel()
                }
                .show()
            false
        }
    }
    interface OnClickListener{
        fun onClickListener(note : Note)
    }
}
class ViewHolder(binding : LayoutRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    private val viewBinding : LayoutRecyclerBinding
    init {
        this.viewBinding = binding
    }
    fun bindData(note : Note){
        viewBinding.title.text = note.getTitle()
        viewBinding.content.text = note.getContent()
    }
    fun getViewBinding(): LayoutRecyclerBinding{
        return viewBinding
    }
}