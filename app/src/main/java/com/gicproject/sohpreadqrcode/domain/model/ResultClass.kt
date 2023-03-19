package com.gicproject.sohpreadqrcode.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ResultClass (
    @SerializedName("return_msg"    ) var Message   : String? = null,
): Serializable