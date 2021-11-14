package com.example.dictionaryapp.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Translation(

    @SerializedName("text")
    val translation: String?,

    @SerializedName("note")
    val note: String?

) : Parcelable
