package com.example.dictionaryapp.model.entities

import com.google.gson.annotations.SerializedName

data class Translation(

    @SerializedName("text")
    val translation: String?,

    @SerializedName("note")
    val note: String?

)
