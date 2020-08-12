package com.adelnor.adeladmin.model.api

import com.adelnor.adeladmin.model.db.MerchandiseArraItem
import com.google.gson.annotations.SerializedName

class MerchandiseArraDocument(

    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nCodAlmInterno") var codAlmInterno: Int = 0,
    @SerializedName("nDomicilioAlmacen") var domiId: String = "",
    @SerializedName("ListaDeProductos") var productsList: ArrayList<MerchandiseArraItem> = ArrayList()


)