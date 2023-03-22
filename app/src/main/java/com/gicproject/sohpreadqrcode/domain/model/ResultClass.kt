package com.gicproject.sohpreadqrcode.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ResultClass (
    @SerializedName("return_msg"    ) var Message   : String? = null,
): Serializable



data class ResultClassAndPatientInfo (
   var resultClass   : ResultClass? = null,
   var patientInfo   : PatientInfo? = null,
): Serializable