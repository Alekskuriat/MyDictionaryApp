package com.example.dictionaryapp.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meanings(

    @SerializedName("translation")
    val translation: Translation?,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("difficultyLevel")
    val difficultyLevel: Int,


) : Parcelable
