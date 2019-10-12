package com.mateus.batista.data_remote.model

import com.google.gson.annotations.SerializedName

data class GeoApi(
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lng")
    val lng: Double? = null
)