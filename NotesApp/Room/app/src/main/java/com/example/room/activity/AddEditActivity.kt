package com.example.room.activity

import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.room.R
import com.example.room.utils.CONSTANTS

class AddEditActivity : AppCompatActivity() {

    private lateinit var ediTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var numberPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit)

        ediTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescrption)
        numberPicker = findViewById(R.id.numberPicker)


        numberPicker.minValue = 0
        numberPicker.maxValue = 10
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.close)
        if(intent.hasExtra(CONSTANTS.EXTRA_ID)){
            title = "Edit Note"
            ediTextTitle.setText(intent.getStringExtra(CONSTANTS.EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(CONSTANTS.EXTRA_DESCRIPTION))
            numberPicker.value = intent.getIntExtra(CONSTANTS.EXTRA_Priority,-1)
        }else{
            title = "Add Note"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu_item -> {
                saveNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = ediTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val priority = numberPicker.value

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please Title and Description", Toast.LENGTH_LONG).show()
            return
        }

        val id = intent.getIntExtra(CONSTANTS.EXTRA_ID,-1)

        if (id != -1){
            setResult(CONSTANTS.EDIT_CODE, Intent().apply {
                putExtra(CONSTANTS.EXTRA_TITLE, title)
                putExtra(CONSTANTS.EXTRA_DESCRIPTION, description)
                putExtra(CONSTANTS.EXTRA_Priority, priority)
                putExtra(CONSTANTS.EXTRA_ID,id)
            })
        }else{
            setResult(CONSTANTS.REQUEST_CODE, Intent().apply {
                putExtra(CONSTANTS.EXTRA_TITLE, title)
                putExtra(CONSTANTS.EXTRA_DESCRIPTION, description)
                putExtra(CONSTANTS.EXTRA_Priority, priority)
            })
        }
        finish()
    }

}