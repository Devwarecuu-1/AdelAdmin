package com.adelnor.adeladmin.model.db

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class SupplierReTagItem(
    @SerializedName("nIdQR") var qrId: Int = 0,
    @SerializedName("nCodProveedor") var supplierId: Int = 0,
    @SerializedName("cRazonSocial") var supplierName: String = "",
    @SerializedName("nCodProductoInterno") var intProductId: Int = 0,
    @SerializedName("nCodProducto") var productId: Int = 0,
    @SerializedName("cDescProducto") var description: String = "",
    @SerializedName("nPresentacion") var presentationId: Int = 0,
    @SerializedName("cDescPresentacion") var presentation: String = "",
    @SerializedName("nCantidad") var quantity: Float = 0f,
    @SerializedName("nFactorEmpaque") var packingFactor: Float = 0f,
    @SerializedName("nFactorEnvase") var packagingFactor: Float = 0f,
    @SerializedName("cTipo") var type: String = "",
    @SerializedName("cLote") var lot: String = "",
    @SerializedName("fechaCaducidad") var expirationDate: String = "",
    @SerializedName("fechaElaboracion") var elaborationDate: String = "",
    @SerializedName("bConsultaCorrecta") var queryOK: Boolean = false
): RealmObject()