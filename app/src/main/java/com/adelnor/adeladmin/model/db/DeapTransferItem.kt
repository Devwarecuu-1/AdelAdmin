package com.adelnor.adeladmin.model.db

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class DeapTransferItem (
    @SerializedName("nCantEntregar") var cantDep: Float = 0f,
    @SerializedName("nMAximaLectura") var nMax: Float = 0f,
    @SerializedName("nIdQR") var niDQR: Int = 0,
    @SerializedName("nCodProductoInterno") var intProductCod: String = "",
    @SerializedName("nCodProducto") var codProd: Int = 0,
    @SerializedName("cDescProducto") var description: String = "",
    @SerializedName("nFolEntrega") var outFol: Int = 0,
    @SerializedName("bctivo") var active: Int = 0,
    @SerializedName("bAsignado") var asigned: Int = 0,
    @SerializedName("nPresentacion") var presentationId: Int = 0,
    @SerializedName("nCantidad") var quantity: Float = 0f,
    @SerializedName("cTipo") var type: String = "",
    @SerializedName("cLote") var lot: String = "",
    @SerializedName("fechaCaducidad") var expirationDate: String = "",
    @SerializedName("fechaElaboracion") var elaborationDate: String = "",
    @SerializedName("EstadoLectura") var read: Boolean = false,
    @SerializedName("cDescPresentacion") var descriptionPresent: String = "",
    @SerializedName("nFactorEnvase") var factorEnvase: Float = 0f,
    @SerializedName("nFactorEmpaque") var  factorEmpaque: Float = 0f

): RealmObject()