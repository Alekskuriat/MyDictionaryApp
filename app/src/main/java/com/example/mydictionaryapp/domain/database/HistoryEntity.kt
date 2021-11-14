package com.example.mydictionaryapp.domain.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = arrayOf(Index(value = arrayOf("word"), unique = true)
    ))
class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    var word: String,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String?

) : Parcelable
