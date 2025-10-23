package com.example.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room.dao.NoteDAO
import com.example.room.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase(){
    abstract fun getNotesDAO() : NoteDAO

    companion object{
        private var INSTANCE: NoteDataBase? = null

        fun getInstance(context: Context) : NoteDataBase{
            return INSTANCE?:synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, NoteDataBase::class.java,"note_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}