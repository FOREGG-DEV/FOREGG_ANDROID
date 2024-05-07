package com.foregg.domain.model.request

import com.google.gson.annotations.SerializedName

data class AccountGetConditionRequestVo(
    @SerializedName("from")
    val from : String,
    @SerializedName("to")
    val to : String
)
