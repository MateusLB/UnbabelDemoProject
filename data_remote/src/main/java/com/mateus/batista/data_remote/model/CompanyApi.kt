package com.mateus.batista.data_remote.model

import com.google.gson.annotations.SerializedName

data class CompanyApi(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("catchPhrase")
    val catchPhrase: String? = null,
    @SerializedName("bs")
    val bs: String? = null
)