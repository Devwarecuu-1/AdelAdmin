package com.adelnor.adeladmin.model.api

import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.Company
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.utils.AppConstants
import com.adelnor.adeladmin.utils.AppConstants.URL_BASIC_CONFIG
import com.adelnor.adeladmin.utils.AppConstants.URL_LOGIN
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01_CANCEL
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01_SAVE
import com.adelnor.adeladmin.utils.AppConstants.URL_PROPERTY_01_UPDATE
import com.adelnor.adeladmin.utils.AppConstants.URL_SUPPLIERS
import com.adelnor.adeladmin.utils.AppConstants.URL_USER_ENT
import retrofit2.Call
import retrofit2.http.*

interface CloudAPI {
    // =============================================================================================
    // COMMON PATHS
    // =============================================================================================
    //region
    @GET(URL_LOGIN)
    fun loginUser(@Query("usr") user: String, @Query("pwd") pass: String): Call<LoginResponse>

    @GET(URL_USER_ENT)
    fun loadCompanies(@Query("cLogin") cLogin: String): Call<List<Company>>

    @GET(URL_BASIC_CONFIG)
    fun loadBranchConfig(@Query("usr") user: String): Call<BranchInfo>

    @GET(URL_BASIC_CONFIG)
    fun loadCustomerInfo(@Query("nIDFacturaCab") folio: Int): Call<CustomerInfo>

    @GET(URL_SUPPLIERS)
    fun loadProvidersList(): Call<List<Supplier>>
    //endregion

    // =============================================================================================
    // PROPERTY PATHS
    // =============================================================================================
    //region
    /*@GET(URL_PROPERTY_01)
    fun loadProvidersList(): Call<List<Supplier>>

    @GET(URL_PROPERTY_01)
    fun loadProductDetails(@Query("nCodProducto") productId: Int,
                           @Query("nPresentacion") presentationId: Int,
                           @Query("nCantidad") Quantity: Float): Call<ItemExtended>

    @GET(URL_PROPERTY_01)
    fun loadPurchaseProvider(@Query("nIDOrdenCompraCab") purchaseId: Int): Call<Supplier>

    @GET(URL_PROPERTY_01)
    fun loadPrevCapture(@Query("nIDQRProveedor") captureFolio: Int,
                        @Query("nCodAlmInterno") warehouseIntId: Int): Call<CaptureRequest>

    @GET(URL_PROPERTY_01_DELETE_ITEM)
    fun deleteItem(@Query("nCodProducto") productId: Int,
                   @Query("nPresentacion") presentationId: Int,
                   @Query("nCantidad") quantity: Float,
                   @Query("cLote") lot: String): Call<ServerResponse>

    @POST(URL_PROPERTY_01_SAVE)
    fun saveCapture(@Body request: CaptureRequest): Call<ServerResponse>

    @POST(URL_PROPERTY_01_UPDATE)
    fun updateCapture(@Body request: CaptureRequest): Call<ServerResponse>

    @GET(URL_PROPERTY_01_CANCEL)
    fun cancelCapture(@Query("nIDQRProveedor") captureFolio: Int,
                      @Query("nCodAlmInterno") warehouseIntId: Int): Call<ServerResponse>
    */

    //endregion



}