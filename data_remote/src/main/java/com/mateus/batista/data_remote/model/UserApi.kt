package com.mateus.batista.data_remote.model

import com.google.gson.annotations.SerializedName

class UserApi (
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("address")
    val address: AddressApi? = null,
    @SerializedName("geo")
    val geo: GeoApi? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("website")
    val website: String? = null,
    @SerializedName("company")
    val company: CompanyApi? = null
)