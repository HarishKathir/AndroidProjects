package com.example.room.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.room.database.NoteDataBase
import com.example.room.model.Note
import com.example.room.repository.NoteRepository
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application){
    val allNotes : LiveData<MutableList<Note>>
    val noteRepository : NoteRepository

    init{
        val dao = NoteDataBase.getInstance(application).getNotesDAO()
        noteRepository = NoteRepository(dao)
        allNotes = noteRepository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.update(note)
    }
    fun insertNote(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }
    fun deleteAllNotes() = viewModelScope.launch {
        noteRepository.deleteAllNotes()
    }
}