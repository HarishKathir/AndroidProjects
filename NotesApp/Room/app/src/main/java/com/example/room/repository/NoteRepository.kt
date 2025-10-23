package com.example.room.repository

import androidx.lifecycle.LiveData
import com.example.room.dao.NoteDAO
import com.example.room.model.Note

class NoteRepository(private val notesDAO: NoteDAO){
    val allNotes : LiveData<MutableList<Note>> = notesDAO.getAll()

    suspend fun insert(note:Note){
        notesDAO.insert(note)
    }

    suspend fun delete(note: Note){
        notesDAO.delete(note)
    }

    suspend fun update(note: Note){
        notesDAO.update(note)
    }

    suspend fun deleteAllNotes(){
        notesDAO.deleteALL()
    }
}