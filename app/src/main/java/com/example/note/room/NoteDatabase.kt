package com.example.note.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class], version = 1)
 abstract class NoteDatabase : RoomDatabase(){
    companion object {

        @Volatile
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context?): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, NoteDatabase::class.java, "NoteDatabase.db")
                    .allowMainThreadQueries().build()
            }
            return instance!!
        }
    }
    abstract fun noteDao() : NoteDAO
}