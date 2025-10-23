package com.example.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.activity.AddEditActivity
import com.example.room.adaptors.NoteAdaptor
import com.example.room.model.Note
import com.example.room.utils.CONSTANTS
import com.example.room.view.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NoteAdaptor.onClickListener{

    private lateinit var noteViewModel: NotesViewModel
    private lateinit var  recycleView : RecyclerView
    private lateinit var  notesadaptor: NoteAdaptor

    private lateinit var addNoteButton: FloatingActionButton
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        addNoteButton = findViewById(R.id.add_button)

        recycleView = findViewById(R.id.recycleView)
        notesadaptor = NoteAdaptor(this)
        recycleView.adapter = notesadaptor
        recycleView.layoutManager = LinearLayoutManager(this)

        noteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[NotesViewModel::class.java]

        noteViewModel.allNotes.observe(this) {
                notesadaptor.submitList(it)
        }

        getResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == CONSTANTS.REQUEST_CODE){
                val title = it.data?.getStringExtra(CONSTANTS.EXTRA_TITLE)
                val description = it.data?.getStringExtra(CONSTANTS.EXTRA_DESCRIPTION)
                val priority = it.data?.getIntExtra(CONSTANTS.EXTRA_Priority,-1)

                val note= Note(title!!,description!!,priority!!)

                noteViewModel.insertNote(note)

            }else if(it.resultCode == CONSTANTS.EDIT_CODE){
                val title = it.data?.getStringExtra(CONSTANTS.EXTRA_TITLE)
                val description = it.data?.getStringExtra(CONSTANTS.EXTRA_DESCRIPTION)
                val priority = it.data?.getIntExtra(CONSTANTS.EXTRA_Priority,-1)
                val id = it.data?.getIntExtra(CONSTANTS.EXTRA_ID,-1)
                val note= Note(title!!,description!!,priority!!)
                note.id = id!!

                noteViewModel.updateNote(note)
            }
        }

        addNoteButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            getResult.launch(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val removedItem = notesadaptor.getNoteAt(viewHolder.bindingAdapterPosition)
                noteViewModel.deleteNote(notesadaptor.getNoteAt(viewHolder.bindingAdapterPosition))

                Snackbar.make(this@MainActivity, recycleView,"Deleted Note", Snackbar.LENGTH_LONG).setAction("UNCO"){
                    noteViewModel.insertNote(removedItem)
                }.show()
            }
        }).attachToRecyclerView(recycleView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu ->{
                noteViewModel.deleteAllNotes()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickItem(note: Note) {
        val title = note.title
        val description = note.description
        val priority = note.priority
        val id = note.id

        val intent = Intent(this@MainActivity, AddEditActivity::class.java)
        intent.putExtra(CONSTANTS.EXTRA_TITLE,title)
        intent.putExtra(CONSTANTS.EXTRA_DESCRIPTION,description)
        intent.putExtra(CONSTANTS.EXTRA_Priority,priority)
        intent.putExtra(CONSTANTS.EXTRA_ID,id)
        getResult.launch(intent)
    }
}