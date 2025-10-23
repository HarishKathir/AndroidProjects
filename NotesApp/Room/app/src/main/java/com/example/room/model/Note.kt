package com.example.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteTable")
data class Note(
    val title: String,
    val description: String,
    val priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}