package com.example.dictionaryapp.model.entities

import com.google.gson.annotations.SerializedName

data class Meanings(

    @SerializedName("translation")
    val translation: Translation?,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("difficultyLevel")
    val difficultyLevel: Int,


)
