package com.example.dictionaryapp.model.entities

import com.google.gson.annotations.SerializedName

data class Translation(

    @field:SerializedName("text")
    val translation: String?

)
