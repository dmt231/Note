package com.example.note.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.note.room.Note

@Dao
interface NoteDAO {
    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("SELECT * FROM Note")
    fun getAllNote() : LiveData<List<Note>>

    @Delete

    fun delete(note: Note)
}