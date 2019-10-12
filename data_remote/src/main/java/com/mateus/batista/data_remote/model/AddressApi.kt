package com.mateus.batista.data_remote.model

import com.google.gson.annotations.SerializedName

data class AddressApi(
    @SerializedName("street")
    val street: String? = null,
    @SerializedName("suite")
    val suite: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("zipcode")
    val zipcode: String? = null
)