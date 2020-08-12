package com.adelnor.adeladmin.model.db

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class OutItemExtended (
    @SerializedName("nIdQR") var qrId: Int = 0,
    @SerializedName("cDomicilio") var address: String = "",
    @SerializedName("nCodProductoInterno") var intProductId: Int = 0,
    @SerializedName("cDescProducto") var description: String = "",
    @SerializedName("nPresentacion") var presentationId: Int = 0,
    @SerializedName("cDescPresentacion") var presentation: String = "",
    @SerializedName("nCantidad") var quantity: Float = 0f,
    @SerializedName("cTipoQR") var type: String = "",
    @SerializedName("cLote") var lot: String = "",
    @SerializedName("dFechaCaducidad") var expirationDate: String = "",
    @SerializedName("dFechaElaboracion") var elaborationDate: String = "",
    @SerializedName("bActivo") var active: Boolean = false,
    @SerializedName("EstadoLectura") var read: Boolean = false
): RealmObject()

