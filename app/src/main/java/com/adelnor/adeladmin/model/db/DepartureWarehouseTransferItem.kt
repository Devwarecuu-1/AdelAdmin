package com.adelnor.adeladmin.model.db

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class DepartureWarehouseTransferItem (
    @SerializedName("nIdQR") var qrId: Int = 0,
    @SerializedName("nCodProductoInterno") var intProductId: Int = 0,
    @SerializedName("nCodProducto") var productId: Int = 0,
    @SerializedName("cDescProducto") var description: String = "",
    @SerializedName("nPresentacion") var presentationId: Int = 0,
    @SerializedName("cDescPresentacion") var presentation: String = "",
    @SerializedName("nCantidad") var quantity: Float = 0f,
    @SerializedName("cTipo") var type: String = "",
    @SerializedName("cLote") var lot: String = "",
    @SerializedName("fechaCaducidad") var expirationDate: String = "",
    @SerializedName("fechaElaboracion") var elaborationDate: String = "",
    @SerializedName("nFactorEmpaque") var packingFactor: Float = 0f,
    @SerializedName("nFactorEnvase") var packagingFactor: Float = 0f,
    @SerializedName("nCantEntregar") var toDeliver: Float = 0f,
    @SerializedName("nMAximaLectura") var maxRead: Float = 0f
): RealmObject()