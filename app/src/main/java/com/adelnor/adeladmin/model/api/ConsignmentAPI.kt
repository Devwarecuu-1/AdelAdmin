package com.adelnor.adeladmin.model.api

import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.repositories.ConsignmentCloudRepository
import com.adelnor.adeladmin.utils.AppConstants
import com.adelnor.adeladmin.utils.AppConstants.URL_CONSIGNMENT_01
import com.adelnor.adeladmin.utils.AppConstants.URL_CONSIGNMENT_01_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_CONSIGNMENT_01_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_CONSIGNMENT_01_UPDATE
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ConsignmentAPI {
    //==============================================================================================
    // 01 - ENTRADA POR CONSIGNACION A PROVEEDOR
    //==============================================================================================
    //region
    @GET(URL_CONSIGNMENT_01)
    fun loadProductDetails01(@Query("nCodProducto") productId: Int,
                             @Query("nPresentacion") presentationId: Int,
                             @Query("nCantidad") quantity: Float): Call<ItemExtended>

    @GET(URL_CONSIGNMENT_01)
    fun validateFolio01(@Query("nIDOrdenCompraCab") purchaseId: Int): Call<Supplier>

    @GET(URL_CONSIGNMENT_01)
    fun loadSavedDocument01(@Query("nIDQRProveedor") documentFolio: Int,
                            @Query("nCodAlmInterno") warehouseIntId: Int): Call<SupplierConsignmentDocument>

    @POST(URL_CONSIGNMENT_01_SAVE)
    fun saveDocument01(@Body request: SupplierConsignmentDocument): Call<ServerResponse>

    @POST(URL_CONSIGNMENT_01_UPDATE)
    fun updateDocument01(@Body request: SupplierConsignmentDocument): Call<ServerResponse>

    @GET(URL_CONSIGNMENT_01_CANCEL)
    fun cancelDocument01(@Query("nIDQRProveedor") documentFolio: Int,
                         @Query("nCodAlmInterno") warehouseIntId: Int,
                         @Query("usuario") user: String,
                         @Query("macAddress") macAddress: String): Call<ServerResponse>
    //endregion


}