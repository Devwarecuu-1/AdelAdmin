package com.adelnor.adeladmin.model.api

import com.adelnor.adeladmin.model.db.*
import com.google.gson.annotations.SerializedName

class BranchInfo(
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("cDescAlmacen") var warehouseName: String = "",
    @SerializedName("cDescSucursal") var branchName: String = "",
    @SerializedName("listaAlmacenes") var warehouses: List<WarehouseConfig> = ArrayList()
)

class WarehouseConfig (
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("cDescAlmacen") var warehouseName: String = ""
){
    override fun toString(): String {
        return "$warehouseId - $warehouseName"
    }
}

class Supplier (
    @SerializedName("nCodProveedor") var supplierId: Int = 0,
    @SerializedName("cRazonSocial") var supplierName: String = "",
    @SerializedName("cNombreCorto") var supplierShortName: String = "",
    @SerializedName("cRFCProveedor") var supplierRFC: String = ""
){
    override fun toString(): String {
        return "$supplierId - $supplierShortName"
    }
}

class RequestInfo (
    @SerializedName("nIDSolNotaCredCab") var requestId: Int = 0,
    @SerializedName("nIDFacturaCab") var invoiceId: Int = 0,
    @SerializedName("nCodCliente") var customerId: Int = 0,
    @SerializedName("cRFC") var customerRFC: String = "",
    @SerializedName("cNombre") var customerName: String = ""
)

class EntryDocument(
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

class DepartureDocument(
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("cRFCCliente") var customerRFC: String = "",
    @SerializedName("cNombre") var customerName: String = "",
    @SerializedName("nIDMovAlmAgroCab") var movAlgId: Int = 0,
    @SerializedName("nAlmacenInterno") var warehouseId: Int = 0,
    @SerializedName("nFolioFactura") var invoiceId: Int = 0,
    @SerializedName("nCodigoSucursal") var branchId: Int = 0,
    @SerializedName("nIDFacturaCab") var invoiceCabId: Int = 0,
    @SerializedName("nIDQRLectura") var captureFolio: Int = 0,
    @SerializedName("modeloSalidaPorEntregaCliente") var productsList: ArrayList<OutItemExtended> = ArrayList()
)

class WarehouseTransferDocument(
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nIDQRLectura") var captureFolio: Int = 0,
    @SerializedName("nFolioAlmacen") var departureFolio: Long = 0,
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("bConsultaCorrecta") var queryOK: Boolean = false,
    @SerializedName("ListaDeProductos") var productsList: ArrayList<WarehouseTransferItem> = ArrayList()
)

class DepartureWarehouseTransferDocument(
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("bConsultaCorrecta") var queryOK: Boolean = false,
    @SerializedName("nIDQRLectura") var captureFolio: Int = 0,
    @SerializedName("nFolEntrega") var deliveryFolio: Long = 0,
    @SerializedName("ListaDeProductos") var productsList: ArrayList<DepartureWarehouseTransferItem> = ArrayList()
)

class PurchaseReturnDocument(
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nIDQRLectura") var captureFolio: Int = 0,
    @SerializedName("nIDFacturaCab") var invoiceCabId: Int = 0,
    @SerializedName("nCodCliente") var customerId: Int = 0,
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("nFolioSolDev") var requestID: Int = 0,
    @SerializedName("ListaDeProductos") var productsList: ArrayList<PurchaseReturnItem> = ArrayList()
)

class SupplierReTagDocument(
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("nCodProveedor") var supplierId: Int = 0,
    @SerializedName("nIDQRLectura") var documentFolio: Int = 0,
    @SerializedName("bConsultaCorrecta") var queryOK: Boolean = false,
    @SerializedName("ListaDeProductosSRPC") var productsList: ArrayList<SupplierReTagItem> = ArrayList()
)

class SupplierReturnDocument(
    @SerializedName("usuario") var user: String = "",
    @SerializedName("macAddress") var macAddress: String = "",
    @SerializedName("nldEmpresa") var companyId: Int = 0,
    @SerializedName("nCodAlmInterno") var warehouseId: Int = 0,
    @SerializedName("nCodSucursal") var branchId: Int = 0,
    @SerializedName("nIDQRProveedor") var supplierIDQR: Int = 0,
    @SerializedName("nIDOrdenCompraCab") var purchaseOrder: Int = 0,
    @SerializedName("nCodProveedor") var supplierId: Int = 0,
    @SerializedName("nIDQRLectura") var documentFolio: Int = 0,
    @SerializedName("bConsultaCorrecta") var queryOK: Boolean = false,
    @SerializedName("ListaDeProductos") var productsList: ArrayList<ItemExtended> = ArrayList()
)