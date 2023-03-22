package com.gicproject.sohpreadqrcode.domain.model



import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PatientInfo (

    @SerializedName("PKID"                  ) var PKID                : String? = null,
    @SerializedName("Children_ID"           ) var ChildrenID          : String? = null,
    @SerializedName("Name"                  ) var Name                : String? = null,
    @SerializedName("Clinics_ID"            ) var ClinicsID           : String? = null,
    @SerializedName("Name_En"               ) var NameEn              : String? = null,
    @SerializedName("Appointment_Status_ID" ) var AppointmentStatusID : String? = null,
    @SerializedName("StatusEn"              ) var StatusEn            : String? = null,
    @SerializedName("SDate"                 ) var SDate               : String? = null,
    @SerializedName("Time"                  ) var Time                : String? = null,
    @SerializedName("Time_am_pm"            ) var TimeAmPm            : String? = null,
    @SerializedName("IsInDaterange"         ) var IsInDaterange       : Int?    = null,
    @SerializedName("CivilID"               ) var CivilID             : String? = null

): Serializable