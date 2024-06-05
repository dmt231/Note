package com.example.note.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.note.room.Note
import com.example.note.room.NoteDatabase

class NoteRepository {
    fun getAllNote(context : Context) : LiveData<List<Note>> {
        return NoteDatabase.getInstance(context).noteDao().getAllNote()
    }
}
