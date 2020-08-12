package com.adelnor.adeladmin.model.api

import com.adelnor.adeladmin.model.db.*
import com.adelnor.adeladmin.utils.AppConstants
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01_UPDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_02
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_02_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_02_LOAD
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_02_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_02_UPDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_02_VALIDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_03_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_03_LOAD
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_03_LOAD_DETAILS
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_03_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_03_UPDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_03_VALIDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_05
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_05_LOAD
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_05_LOAD_DOC
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_05_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_05_UPDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_07
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_07_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_07_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_07_UPDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_08
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_08_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_08_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_08_UPDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_13
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_13_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_13_LOAD_DOC
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_13_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_13_UPDATE
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PropertyAPI {

    //==============================================================================================
    // 01 - ENTRADA POR COMPRA A PROVEEDOR
    //==============================================================================================
    //region
    @GET(URL_PROPERTY_01)
    fun loadProductDetails01(@Query("nCodProducto") productId: Int,
                           @Query("nPresentacion") presentationId: Int,
                           @Query("nCantidad") Quantity: Float): Call<ItemExtended>

    @GET(URL_PROPERTY_01)
    fun loadPurchaseProvider01(@Query("nIDOrdenCompraCab") purchaseId: Int): Call<Supplier>

    @GET(URL_PROPERTY_01)
    fun loadDocument01(@Query("nIDQRProveedor") captureFolio: Int,
                       @Query("nCodAlmInterno") warehouseIntId: Int): Call<EntryDocument>

    @POST(URL_PROPERTY_01_SAVE)
    fun saveDocument01(@Body request: EntryDocument): Call<ServerResponse>

    @POST(URL_PROPERTY_01_UPDATE)
    fun updateDocument01(@Body request: EntryDocument): Call<ServerResponse>

    @GET(URL_PROPERTY_01_CANCEL)
    fun cancelDocument01(@Query("nIDQRProveedor") captureFolio: Int,
                      @Query("nCodAlmInterno") warehouseIntId: Int): Call<ServerResponse>
    //endregion

    //==============================================================================================
    // 02 - ENTRADA POR TRANSFERENCIA DE ALMACEN
    //==============================================================================================
    //region
    @GET(URL_PROPERTY_02_VALIDATE)
    fun validateFolio02(@Query("nFolioAlmacen") requestFolio: Long,
                        @Query("nCodAlmInterno") warehouseIntId: Int): Call<ServerResponse>

    @GET(URL_PROPERTY_02)
    fun loadProductDetails02(@Query("nidQR") qrCode: Int,
                             @Query("nCodProducto") prodId: Int,
                             @Query("nPresentacion") presentationId: Int,
                             @Query("nCantidad") quantity: Float): Call<WarehouseTransferItem>

    @GET(URL_PROPERTY_02_LOAD)
    fun loadSavedDocument02(@Query("nIDQRLectura") documentFolio: Int,
                            @Query("nCodAlmInterno") warehouseIntId: Int): Call<WarehouseTransferDocument>

    @POST(URL_PROPERTY_02_SAVE)
    fun saveDocument02(@Body request: WarehouseTransferDocument): Call<ServerResponse>

    @POST(URL_PROPERTY_02_UPDATE)
    fun updateDocument02(@Body request: WarehouseTransferDocument): Call<ServerResponse>

    @GET(URL_PROPERTY_02_CANCEL)
    fun cancelDocument02(@Query("nIDQRLectura") captureFolio: Int,
                         @Query("nCodAlmInterno") warehouseIntId: Int): Call<ServerResponse>
    //endregion

    //==============================================================================================
    // 03 - ENTRADA POR DEVOLUCION A CLIENTE
    //==============================================================================================
    //region
    @GET(URL_PROPERTY_03_VALIDATE)
    fun validateFolio03(@Query("nIDsolNotaCredCab") requestFolio: Int,
                        @Query("nCodAlmInterno") warehouseIntId: Int): Call<RequestInfo>

    @GET(URL_PROPERTY_03_LOAD)
    fun loadSavedDocument03(@Query("nIDQRLectura") documentFolio: Int,
                            @Query("nCodAlmInterno") warehouseIntId: Int): Call<PurchaseReturnDocument>

    @GET(URL_PROPERTY_03_LOAD_DETAILS)
    fun loadProductDetails03(@Query("nIdQR") qrCode: Int,
                             @Query("nCodProducto") prodId: Int,
                             @Query("nPresentacion") presentationId: Int,
                             @Query("nCantidad") quantity: Float): Call<PurchaseReturnItem>

    @POST(URL_PROPERTY_03_SAVE)
    fun saveDocument03(@Body request: PurchaseReturnDocument): Call<ServerResponse>

    @POST(URL_PROPERTY_03_UPDATE)
    fun updateDocument03(@Body request: PurchaseReturnDocument): Call<ServerResponse>

    @GET(URL_PROPERTY_03_CANCEL)
    fun cancelDocument03(@Query("nIDQRProveedor") captureFolio: Int,
                         @Query("nCodAlmInterno") warehouseIntId: Int,
                         @Query("cUsuarioEliminacion") user: String,
                         @Query("cMaquinaEliminacion") macAddress: String): Call<ServerResponse>
    //endregion
    //==============================================================================================
    // 04 SALIDA POR TRANSFERENCIA ALMACEN
    //==============================================================================================
    @GET(AppConstants.URL_PROPERTY_04)
    fun loadProductsDetails04(@Query("niDQR") qrCode: Int,
                              @Query("nCodProducto") prodId: Int,
                              @Query("nCantidad") quantity: Float,
                              @Query("nPresentacion") presentationId: Int,
                              @Query("nFolEntrega") folEntrega: Int): Call<DeapTransferItem>
    @GET(AppConstants.URL_PROPERTY_04_CANCEL)
    fun cancelDocument04(@Query("nIDQRProveedor") supplierId: Int,
                         @Query("nCodAlmInterno") warehouseId: Int,
                         @Query("usuario") user: String,
                         @Query("macAddress") macAddress: String
    ): Call<ServerResponse>

    @POST(AppConstants.URL_PROPERTY_04_SAVE)
    fun saveDocument04(@Body request: DepartureTransferDocument): Call<ServerResponse>

    @POST(AppConstants.URL_PROPERTY_04_UPDATE)
    fun updateDocument04(@Body request: DepartureTransferDocument): Call<ServerResponse>

    @GET(AppConstants.URL_PROPERTY_04_LOAD_DOC)
    fun loadDocument04(@Query("nIDQRLectura") qrCode: Int,
                       @Query("nCodAlmInterno") prodId: Int): Call<DepartureTransferDocument>


    //==============================================================================================
    // 05 - SALIDA POR ENTREGA FISICA A CLIENTE
    //==============================================================================================
    //region
    @GET(URL_PROPERTY_05_LOAD)
    fun loadDocument05(@Query("nFolioFactura") invoiceId: Int,
                       @Query("nAlmacenInterno") warehouseId: Int): Call<DepartureDocument>

    @GET(URL_PROPERTY_05_LOAD_DOC)
    fun loadSavedDocument05(@Query("nIDQRLectura") documentFolio: Int,
                            @Query("nCodAlmInterno") warehouseId: Int): Call<DepartureDocument>

    /*@GET(URL_PROPERTY_05)
    fun loadProductDetails05(@Query("nidQR") qrCode: Int,
                          @Query("nCodProducto") prodId: Int,
                          @Query("nPresentacion") presentationId: Int,
                          @Query("nCantidad") quantity: Float): Call<OutItemExtended>*/

    @POST(URL_PROPERTY_05_SAVE)
    fun saveDocument05(@Body request: DepartureDocument): Call<ServerResponse>

    @POST(URL_PROPERTY_05_UPDATE)
    fun updateDocument05(@Body request: DepartureDocument): Call<ServerResponse>

    @GET(URL_PROPERTY_05)
    fun cancelDocument05(@Query("nIDQRLectura") documentFolio: Int,
                         @Query("nCodAlmInterno") warehouseId: Int,
                         @Query("usuario") user: String,
                         @Query("macAddress") macAddress: String): Call<ServerResponse>
    //endregion

    //==============================================================================================
    // 07 - SALIDA POR TRANSFERENCIA
    //==============================================================================================
    //region
    @GET(URL_PROPERTY_07)
    fun loadSavedDocument07(@Query("nIDQRLectura") documentFolio: Int,
                            @Query("nCodAlmInterno") warehouseIntId: Int): Call<DepartureWarehouseTransferDocument>

    @GET(URL_PROPERTY_07)
    fun loadProductDetails07(@Query("nIdQR") qrCode: Int,
                             @Query("nCodProducto") prodId: Int,
                             @Query("nPresentacion") presentationId: Int,
                             @Query("nCantidad") quantity: Float,
                             @Query("nFolEntrega") deliveryFolio: Long): Call<DepartureWarehouseTransferItem>

    @POST(URL_PROPERTY_07_SAVE)
    fun saveDocument07(@Body request: DepartureWarehouseTransferDocument): Call<ServerResponse>

    @POST(URL_PROPERTY_07_UPDATE)
    fun updateDocument07(@Body request: DepartureWarehouseTransferDocument): Call<ServerResponse>

    @GET(URL_PROPERTY_07_CANCEL)
    fun cancelDocument07(@Query("nIDQRLectura") documentFolio: Long,
                         @Query("nCodAlmInterno") warehouseIntId: Int,
                         @Query("usuario") user: String,
                         @Query("macAddress") macAddress: String): Call<ServerResponse>
    //endregion

    //==============================================================================================
    // 08 - SALIDA POR DEVOLUCION A PROVEEDOR
    //==============================================================================================
    //region
    @GET(URL_PROPERTY_08)
    fun loadProductDetails08(@Query("nIdQR") qrId: Int,
                             @Query("nCodProducto") productId: Int,
                             @Query("nPresentacion") presentationId: Int,
                             @Query("nCantidad") Quantity: Float): Call<ItemExtended>

    @GET(URL_PROPERTY_08)
    fun loadSavedDocument08(@Query("nIDQRProveedor") captureFolio: Int,
                            @Query("nCodAlmInterno") warehouseIntId: Int): Call<SupplierReturnDocument>

    @POST(URL_PROPERTY_08_SAVE)
    fun saveDocument08(@Body request: SupplierReturnDocument): Call<ServerResponse>

    @POST(URL_PROPERTY_08_UPDATE)
    fun updateDocument08(@Body request: SupplierReturnDocument): Call<ServerResponse>

    @GET(URL_PROPERTY_08_CANCEL)
    fun cancelDocument08(@Query("nIDQRProveedor") captureFolio: Int,
                         @Query("nCodAlmInterno") warehouseIntId: Int): Call<ServerResponse>

    //endregion

    //==============================================================================================
    // 13 - SALIDA POR REETIQUETADO POR PROVEEDOR
    //==============================================================================================
    //region
    @GET(URL_PROPERTY_13)
    fun loadProductDetails13(@Query("nIdQR") qrCode: Int,
                             @Query("nCodProducto") prodId: Int,
                             @Query("nPresentacion") presentationId: Int,
                             @Query("nCantidad") quantity: Int): Call<SupplierReTagItem>

    @GET(URL_PROPERTY_13_LOAD_DOC)
    fun loadSavedDocument13(@Query("nIDQRLectura") documentFolio: Int,
                            @Query("nCodAlmInterno") warehouseId: Int): Call<SupplierReTagDocument>

    @POST(URL_PROPERTY_13_SAVE)
    fun saveDocument13(@Body request: SupplierReTagDocument): Call<ServerResponse>

    @POST(URL_PROPERTY_13_UPDATE)
    fun updateDocument13(@Body request: SupplierReTagDocument): Call<ServerResponse>

    @GET(URL_PROPERTY_13_CANCEL)
    fun cancelDocument13(@Query("nIDQRLectura") documentFolio: Int,
                         @Query("nCodAlmInterno") warehouseId: Int): Call<ServerResponse>

    //==============================================================================================
    // Pantalla AcomodoMercancias
    //==============================================================================================
    @GET(AppConstants.URL_PROPERTY_82)
    fun loadDomi(@Query("nAlmacenInterno") nAlmacenInterno: Int,
                 @Query("nDomicilio") nDomicilio: String): Call<DomiDat>
    @GET(AppConstants.URL_PROPERTY_82_PROD)
    fun loadProductsDetails82(@Query("niDQR") qrCode: Int,
                              @Query("nCodProducto") prodId: Int,
                              @Query("nCantidad") quantity: Float,
                              @Query("nPresentacion") presentationId: Int): Call<DeapTransferItem2>

    @POST(AppConstants.URL_PROPERTY_82_SAVE)
    fun saveDocument82(@Body request: MerchandiseArraDocument): Call<ServerResponse>
    //endregion
    //==============================================================================================
    // 020 SALIDA POR TRANSFERENCIA ALMACEN PROPIO
    //==============================================================================================
    @GET(AppConstants.URL_PROPERTY_020)
    fun loadProductsDetails020(@Query("niDQR") qrCode: Int,
                              @Query("nCodProducto") prodId: Int,
                              @Query("nCantidad") quantity: Float,
                              @Query("nPresentacion") presentationId: Int,
                              @Query("nFolEntrega") folEntrega: Int): Call<DeapTransferItem>
    @GET(AppConstants.URL_PROPERTY_020_CANCEL)
    fun cancelDocument020(@Query("nIDQRProveedor") supplierId: Int,
                         @Query("nCodAlmInterno") warehouseId: Int,
                         @Query("usuario") user: String,
                         @Query("macAddress") macAddress: String
    ): Call<ServerResponse>

    @POST(AppConstants.URL_PROPERTY_020_SAVE)
    fun saveDocument020(@Body request: DepartureTransferDocument): Call<ServerResponse>

    @POST(AppConstants.URL_PROPERTY_020_UPDATE)
    fun updateDocument020(@Body request: DepartureTransferDocument): Call<ServerResponse>

    @GET(AppConstants.URL_PROPERTY_020_LOAD_DOC)
    fun loadDocument020(@Query("nIDQRLectura") qrCode: Int,
                       @Query("nCodAlmInterno") prodId: Int): Call<DepartureTransferDocument>


}