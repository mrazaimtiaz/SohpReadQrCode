package com.gicproject.sohpreadqrcode.data.remote.dto

import com.gicproject.emojisurveyapp.domain.model.Location
import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Name") var Name: String? = null
){
    fun toLocation(): Location {
        return Location(
            ID = ID,
            Name = Name,
        )
    }
}
