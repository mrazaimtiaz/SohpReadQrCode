package com.gicproject.emojisurveyapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class CustomerInput(
    val questionId: String,
    val location: String,
    val mobileNumber: String,
    val timestamp: Long,
    val answerId: String,
    val locationId: String,
    val value: String,
    var isSaved: Int,
    var showInfoPage: Boolean? = null,
    var sendEmail: Boolean? = null,
    var clinicId: Int? = null,
    var clinicName: String? = null,
    @PrimaryKey var id: Int? = null
)

class InvalidCustomerInputException(message: String): Exception(message)

