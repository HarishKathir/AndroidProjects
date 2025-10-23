package com.example.room.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.model.Note

class NoteAdaptor(val listener: onClickListener) : ListAdapter<Note, NoteAdaptor.noteViewholder>(DIFF_CALLBACK){

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


    private var notesList : MutableList<Note> = mutableListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): noteViewholder {
        return noteViewholder(LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false))
    }

    override fun onBindViewHolder(
        holder: noteViewholder,
        position: Int
    ) {
        val note = getItem(position)
        holder.textTitle.text = note.title
        holder.textDesciption.text = note.description
        holder.textPriority.text = note.priority.toString()
    }

    fun getNoteAt(position: Int):Note{
        return getItem(position)
    }

    inner class noteViewholder(view: View) : RecyclerView.ViewHolder(view){
         val textTitle : TextView = view.findViewById(R.id.textView_Title)
         val textDesciption : TextView = view.findViewById(R.id.textView_description)
         val textPriority : TextView = view.findViewById(R.id.textView_priority)

        init {
            view.setOnClickListener {
                if(bindingAdapterPosition != RecyclerView.NO_POSITION){
                    listener.onClickItem(getItem(bindingAdapterPosition))
                }
            }
        }

    }


    interface onClickListener{
        fun onClickItem(note: Note)
    }
}