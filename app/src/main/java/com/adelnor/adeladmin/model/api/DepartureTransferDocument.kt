package com.adelnor.adeladmin.model.api

import com.adelnor.adeladmin.model.db.DeapTransferItemPost
import com.google.gson.annotations.SerializedName

class DepartureTransferDocument(

    @SerializedName("nIDQRLectura") var captureFolio: Int = 0,
    @SerializedName("nFolEntrega") var folioEntrega: Int = 0,
    @SerializedName("ListaDeProductos") var productsList: ArrayList<DeapTransferItemPost> = ArrayList(),
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nCodAlmInterno") var codAlmInterno: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("bConsultaCorrecta") var consul: Boolean = true

)