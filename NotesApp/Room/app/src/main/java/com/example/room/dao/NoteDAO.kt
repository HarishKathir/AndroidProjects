package com.example.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.room.model.Note

@Dao
interface NoteDAO{
    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note:Note)

    @Update
    suspend fun update(note:Note)

    @Query("DELETE FROM noteTable")
    suspend fun deleteALL()

    @Query("SELECT * FROM noteTable ORDER BY priority ASC")
    fun getAll() : LiveData<MutableList<Note>>
}