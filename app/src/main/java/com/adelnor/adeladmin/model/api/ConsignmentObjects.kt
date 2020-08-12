package com.adelnor.adeladmin.model.api

import com.adelnor.adeladmin.model.db.ItemExtended
import com.google.gson.annotations.SerializedName

class SupplierConsignmentDocument(
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nldEmpresa") var companyId: Int = 0,
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("nCodProveedor") var supplierId: Int = 0,
    @SerializedName("nIDQRProveedor") var supplierIDQR: Int = 0,
    @SerializedName("nIDOrdenCompraCab") var purchaseOrder: Int = 0,
    @SerializedName("ListaDeProductos") var productsList: ArrayList<ItemExtended> = ArrayList()
)