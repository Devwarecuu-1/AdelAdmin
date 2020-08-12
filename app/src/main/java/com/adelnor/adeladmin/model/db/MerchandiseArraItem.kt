package com.adelnor.adeladmin.model.db

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class MerchandiseArraItem (

    @SerializedName("nIdQR") var niDQR: Int = 0, //
    @SerializedName("nCodProductoInterno") var intProductCod: String = "", //
    @SerializedName("cDescProducto") var description: String = "", //
    @SerializedName("nPresentacion") var presentationId: Int = 0, //
    @SerializedName("cDescripcion") var cDescr: String = "", //
    @SerializedName("cTipoQR") var cTipo: String = "", //
    @SerializedName("nCantidad") var quantity: Float = 0f, //
    @SerializedName("cLote") var lot: String = "", //
    @SerializedName("fechaCaducidad") var expirationDate: String = "", //
    @SerializedName("fechaElaboracion") var elaborationDate: String = "", //
    @SerializedName("EstadoLectura") var estado: Boolean = false

): RealmObject()