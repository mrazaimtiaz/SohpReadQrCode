package com.gicproject.salamattendanceapp.data.remote.dto

import com.gicproject.emojisurveyapp.domain.model.ResultId
import com.google.gson.annotations.SerializedName

data class ResultIdDto(
    @SerializedName("ID") var ID: Int? = null
) {
    fun toResultId(): ResultId {
        return ResultId(
            ID = ID
        )
    }
}
