package com.adelnor.adeladmin.model.db

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Company(
    @PrimaryKey @SerializedName("nldEmpresa") var companyId: Int = 0,
    @SerializedName("cRFC") var rfc: String = "",
    @SerializedName("cRazonSocial") var companyName: String = "",
    @SerializedName("cNombreCorto") var companyShortName: String = "",
    @SerializedName("bActivo") var active: Boolean = true

): RealmObject() {
    override fun toString(): String {
        return companyName
    }
}