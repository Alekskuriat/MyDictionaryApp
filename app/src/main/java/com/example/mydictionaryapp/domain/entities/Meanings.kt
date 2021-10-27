package com.example.dictionaryapp.model.entities

import com.google.gson.annotations.SerializedName

data class Meanings(

    @field:SerializedName("translation")
    val translation: Translation?,

    @field:SerializedName("imageUrl")
    val imageUrl: String?,

    @field:SerializedName("difficultyLevel")
    val difficultyLevel: Int,


)
