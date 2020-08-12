package com.adelnor.adeladmin.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class PropertyCloudRepository {

    private var cloudApi: PropertyAPI

    init {
        var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.MINUTES)
            .readTimeout(8, TimeUnit.MINUTES)
            .writeTimeout(8, TimeUnit.MINUTES)
            .build()

        var URL = if(RealmRepository().selectSessionData()!=null)
            RealmRepository().selectSessionData()!!.mainUrl
        else "http://104.215.117.162/"

        cloudApi = Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PropertyAPI::class.java)
    }

    //==============================================================================================
    // 01 ENTRADA POR COMPRA A PROVEEDOR
    //==============================================================================================
    //region

    fun loadProviderByPurchaseCode(purchaseId: Int): MutableLiveData<Supplier> {
        var mResponse = MutableLiveData<Supplier>()
        cloudApi.loadPurchaseProvider01(purchaseId).enqueue(object: Callback<Supplier> {
            override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                Log.e("PROVIDER_RESPONSE", "ResultCode: " + response.code())
                Log.e("PROVIDER_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<Supplier>, t: Throwable) {
                Log.e("PROVIDER_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }

    fun getProductDetails01(productId: Int, presentationId: Int, quantity: Float): MutableLiveData<ItemExtended> {
        var mResponse = MutableLiveData<ItemExtended>()
        cloudApi.loadProductDetails01(productId, presentationId, quantity).enqueue(object: Callback<ItemExtended>{
            override fun onResponse(call: Call<ItemExtended>, response: Response<ItemExtended>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ItemExtended>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun loadDocument01(providerId: Int, warehouseIntId: Int): MutableLiveData<EntryDocument> {
        var mResponse = MutableLiveData<EntryDocument>()
        cloudApi.loadDocument01(providerId, warehouseIntId).enqueue(object: Callback<EntryDocument>{
            override fun onResponse(call: Call<EntryDocument>, response: Response<EntryDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<EntryDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun saveDocument01(request: EntryDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument01(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun updateDocument01(request: EntryDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument01(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun cancelDocument01(documentFolio: Int, warehouseIntId: Int): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument01(documentFolio, warehouseIntId).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }
    //endregion

    //==============================================================================================
    // 02 ENTRADA POR TRANSFERENCIA DE ALMACEN
    //==============================================================================================
    //region
    fun validateFolio02(requestFolio: Long, warehouseId: Int): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.validateFolio02(requestFolio, warehouseId).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("VALIDATE_RESPONSE", "ResultCode: " + response.code())
                Log.e("VALIDARE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("VALIDATE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun getProductDetails02(qrCode: Int, productId: Int, presentationId: Int, quantity: Float): MutableLiveData<WarehouseTransferItem> {
        var mResponse = MutableLiveData<WarehouseTransferItem>()
        cloudApi.loadProductDetails02(qrCode, productId, presentationId, quantity).enqueue(object: Callback<WarehouseTransferItem>{
            override fun onResponse(call: Call<WarehouseTransferItem>, response: Response<WarehouseTransferItem>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<WarehouseTransferItem>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun loadSavedDocument02(providerId: Int, warehouseIntId: Int): MutableLiveData<WarehouseTransferDocument> {
        var mResponse = MutableLiveData<WarehouseTransferDocument>()
        cloudApi.loadSavedDocument02(providerId, warehouseIntId).enqueue(object: Callback<WarehouseTransferDocument>{
            override fun onResponse(call: Call<WarehouseTransferDocument>, response: Response<WarehouseTransferDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<WarehouseTransferDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun saveDocument02(request: WarehouseTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument02(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun updateDocument02(request: WarehouseTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument02(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun cancelDocument02(documentFolio: Int, warehouseIntId: Int): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument02(documentFolio, warehouseIntId).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }
    //endregion

    //==============================================================================================
    // 03 ENTRADA POR DEVOLUCION A CLIENTE
    //==============================================================================================
    //region
    fun validateFolio03(requestFolio: Int, warehouseId: Int): MutableLiveData<RequestInfo> {
        var mResponse = MutableLiveData<RequestInfo>()
        cloudApi.validateFolio03(requestFolio, warehouseId).enqueue(object: Callback<RequestInfo> {
            override fun onResponse(call: Call<RequestInfo>, response: Response<RequestInfo>) {
                Log.e("VALIDATE_RESPONSE", "ResultCode: " + response.code())
                Log.e("VALIDARE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<RequestInfo>, t: Throwable) {
                Log.e("VALIDATE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }

    fun loadSavedDocument03(requestFolio: Int, warehouseIntId: Int): MutableLiveData<PurchaseReturnDocument> {
        var mResponse = MutableLiveData<PurchaseReturnDocument>()
        cloudApi.loadSavedDocument03(requestFolio, warehouseIntId).enqueue(object: Callback<PurchaseReturnDocument>{
            override fun onResponse(call: Call<PurchaseReturnDocument>, response: Response<PurchaseReturnDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<PurchaseReturnDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun getProductDetails03(qrCode: Int, prodId: Int, presentationId: Int, quantity: Float): MutableLiveData<PurchaseReturnItem> {
        var mResponse = MutableLiveData<PurchaseReturnItem>()
        cloudApi.loadProductDetails03(qrCode, prodId, presentationId, quantity).enqueue(object: Callback<PurchaseReturnItem>{
            override fun onResponse(call: Call<PurchaseReturnItem>, response: Response<PurchaseReturnItem>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<PurchaseReturnItem>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun saveDocument03(request: PurchaseReturnDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument03(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun updateDocument03(request: PurchaseReturnDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument03(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun cancelDocument03(captureFolio: Int, warehouseId: Int, user: String, macAddress: String): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument03(captureFolio, warehouseId, user, macAddress).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }

    //endregion
    //==============================================================================================
    // 04 - SALIDA POR TRANSFEREMCIA ALMACEN
    //==============================================================================================
    fun getProductDetails04(qrCode: Int,prodId: Int, quantity: Float, presentationId: Int,  folEntrega: Int): MutableLiveData<DeapTransferItem> {
        var mResponse = MutableLiveData<DeapTransferItem>()
        cloudApi.loadProductsDetails04(qrCode, prodId,  quantity, presentationId, folEntrega).enqueue(object: Callback<DeapTransferItem>{
            override fun onResponse(call: Call<DeapTransferItem>, response: Response<DeapTransferItem>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DeapTransferItem>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }
    fun cancelDocument04(documentFolio: Int, warehouseIntId: Int, user: String, macAddress: String): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument04(documentFolio, warehouseIntId, user, macAddress).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }
    fun saveDocument04(request: DepartureTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument04(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }
    fun loadDocument04(qrCode: Int, prodId: Int): MutableLiveData<DepartureTransferDocument> {
        var mResponse = MutableLiveData<DepartureTransferDocument>()
        cloudApi.loadDocument04(qrCode,prodId).enqueue(object: Callback<DepartureTransferDocument>{
            override fun onResponse(call: Call<DepartureTransferDocument>, response: Response<DepartureTransferDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DepartureTransferDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }
    fun updateDocument04(request: DepartureTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument04(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    //==============================================================================================
    // 05 - SALIDA POR ENTREGA FISICA CLIENTE
    //==============================================================================================
    //region
    fun loadDocument05(invoiceId: Int, warehouseIntId: Int): MutableLiveData<DepartureDocument> {
        var mResponse = MutableLiveData<DepartureDocument>()

        cloudApi.loadDocument05(invoiceId, warehouseIntId).enqueue(object: Callback<DepartureDocument>{
            override fun onResponse(call: Call<DepartureDocument>, response: Response<DepartureDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DepartureDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun loadSavedDocument05(documentFolio: Int, warehouseId: Int): MutableLiveData<DepartureDocument> {
        var mResponse = MutableLiveData<DepartureDocument>()
        cloudApi.loadSavedDocument05(documentFolio, warehouseId).enqueue(object: Callback<DepartureDocument>{
            override fun onResponse(call: Call<DepartureDocument>, response: Response<DepartureDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DepartureDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun saveDocument05(request: DepartureDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument05(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun updateDocument05(request: DepartureDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument05(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun cancelDocument05(documentFolio: Int, warehouseId: Int, user: String, macAddress: String): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument05(documentFolio, warehouseId, user, macAddress).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }
    //endregion

    //==============================================================================================
    // 07 SALIDA POR TRANSFERENCIA
    //==============================================================================================
    //region
    fun loadSavedDocument07(requestFolio: Int, warehouseIntId: Int): MutableLiveData<DepartureWarehouseTransferDocument> {
        var mResponse = MutableLiveData<DepartureWarehouseTransferDocument>()
        cloudApi.loadSavedDocument07(requestFolio, warehouseIntId).enqueue(object: Callback<DepartureWarehouseTransferDocument>{
            override fun onResponse(call: Call<DepartureWarehouseTransferDocument>, response: Response<DepartureWarehouseTransferDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DepartureWarehouseTransferDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun getProductDetails07(qrCode: Int, prodId: Int, presentationId: Int, quantity: Float, deliveryFolio: Long): MutableLiveData<DepartureWarehouseTransferItem> {
        var mResponse = MutableLiveData<DepartureWarehouseTransferItem>()
        cloudApi.loadProductDetails07(qrCode, prodId, presentationId, quantity, deliveryFolio).enqueue(object: Callback<DepartureWarehouseTransferItem>{
            override fun onResponse(call: Call<DepartureWarehouseTransferItem>, response: Response<DepartureWarehouseTransferItem>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DepartureWarehouseTransferItem>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun saveDocument07(request: DepartureWarehouseTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument07(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun updateDocument07(request: DepartureWarehouseTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument07(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun cancelDocument07(deliveryFolio: Long, warehouseId: Int, user: String, macAddress: String): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument07(deliveryFolio, warehouseId, user, macAddress).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }

    //endregion

    //==============================================================================================
    // 08 ENTRADA POR DEVOLUCION A PROVEEDOR
    //==============================================================================================
    //region
    fun getProductDetails08(qrId: Int, productId: Int, presentationId: Int, quantity: Float): MutableLiveData<ItemExtended> {
        var mResponse = MutableLiveData<ItemExtended>()
        cloudApi.loadProductDetails08(qrId, productId, presentationId, quantity).enqueue(object: Callback<ItemExtended>{
            override fun onResponse(call: Call<ItemExtended>, response: Response<ItemExtended>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ItemExtended>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun loadSavedDocument08(captureFolio: Int, warehouseIntId: Int): MutableLiveData<SupplierReturnDocument> {
        var mResponse = MutableLiveData<SupplierReturnDocument>()
        cloudApi.loadSavedDocument08(captureFolio, warehouseIntId).enqueue(object: Callback<SupplierReturnDocument>{
            override fun onResponse(call: Call<SupplierReturnDocument>, response: Response<SupplierReturnDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<SupplierReturnDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun saveDocument08(request: SupplierReturnDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument08(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun updateDocument08(request: SupplierReturnDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument08(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun cancelDocument08(documentFolio: Int, warehouseIntId: Int): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument08(documentFolio, warehouseIntId).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }
    //endregion

    //==============================================================================================
    // 13 - SALIDA POR REETIQUETADO POR PROVEEDOR
    //==============================================================================================
    //region
    fun getProductDetails13(qrCode: Int, prodId: Int, presentationId: Int, quantity: Int): MutableLiveData<SupplierReTagItem> {
        var mResponse = MutableLiveData<SupplierReTagItem>()
        cloudApi.loadProductDetails13(qrCode, prodId, presentationId, quantity).enqueue(object: Callback<SupplierReTagItem>{
            override fun onResponse(call: Call<SupplierReTagItem>, response: Response<SupplierReTagItem>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<SupplierReTagItem>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun loadSavedDocument13(invoiceId: Int, warehouseIntId: Int): MutableLiveData<SupplierReTagDocument> {
        var mResponse = MutableLiveData<SupplierReTagDocument>()
        cloudApi.loadSavedDocument13(invoiceId, warehouseIntId).enqueue(object: Callback<SupplierReTagDocument>{
            override fun onResponse(call: Call<SupplierReTagDocument>, response: Response<SupplierReTagDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<SupplierReTagDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }


        })

        return mResponse
    }

    fun saveDocument13(request: SupplierReTagDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument13(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun updateDocument13(request: SupplierReTagDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument13(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

    fun cancelDocument13(documentFolio: Int, warehouseId: Int): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument13(documentFolio, warehouseId).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }

//==============================================================================================
    // Pantalla Acomodo de mercancia
    //==============================================================================================
    fun getDomi82(nAlmacenInterno: Int, nDomicilio: String): MutableLiveData<DomiDat> {
        var mResponse = MutableLiveData<DomiDat>()
        cloudApi.loadDomi(nAlmacenInterno, nDomicilio).enqueue(object: Callback<DomiDat>{
            override fun onResponse(call: Call<DomiDat>, response: Response<DomiDat>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DomiDat>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }
    fun getProductDetails82(qrCode: Int,prodId: Int, quantity: Float, presentationId: Int): MutableLiveData<DeapTransferItem2> {
        var mResponse = MutableLiveData<DeapTransferItem2>()
        cloudApi.loadProductsDetails82(qrCode, prodId,  quantity, presentationId).enqueue(object: Callback<DeapTransferItem2>{
            override fun onResponse(call: Call<DeapTransferItem2>, response: Response<DeapTransferItem2>) {
                Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
                Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DeapTransferItem2>, t: Throwable) {
                Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }
    fun saveDocument82(request: MerchandiseArraDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument82(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }
//endregion
//==============================================================================================
// 020- SALIDA POR TRANSFEREMCIA ALMACEN PROPIO
//==============================================================================================
fun getProductDetails020(qrCode: Int,prodId: Int, quantity: Float, presentationId: Int,  folEntrega: Int): MutableLiveData<DeapTransferItem> {
    var mResponse = MutableLiveData<DeapTransferItem>()
    cloudApi.loadProductsDetails04(qrCode, prodId,  quantity, presentationId, folEntrega).enqueue(object: Callback<DeapTransferItem>{
        override fun onResponse(call: Call<DeapTransferItem>, response: Response<DeapTransferItem>) {
            Log.e("PRODUCT_DETAIL_RESPONSE", "ResultCode: " + response.code())
            Log.e("PRODUCT_DETAIL_RESPONSE", "" + Gson().toJson(response.body()))
            mResponse.value = response.body()
        }

        override fun onFailure(call: Call<DeapTransferItem>, t: Throwable) {
            Log.e("PRODUCT_DETAIL_ERROR", ""+t.message)
            mResponse.value = null
        }
    })

    return mResponse
}
    fun cancelDocument020(documentFolio: Int, warehouseIntId: Int, user: String, macAddress: String): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument04(documentFolio, warehouseIntId, user, macAddress).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }

        })

        return mResponse
    }
    fun saveDocument020(request: DepartureTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.saveDocument04(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }
    fun loadDocument020(qrCode: Int, prodId: Int): MutableLiveData<DepartureTransferDocument> {
        var mResponse = MutableLiveData<DepartureTransferDocument>()
        cloudApi.loadDocument04(qrCode,prodId).enqueue(object: Callback<DepartureTransferDocument>{
            override fun onResponse(call: Call<DepartureTransferDocument>, response: Response<DepartureTransferDocument>) {
                Log.e("READ_CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<DepartureTransferDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }
    fun updateDocument020(request: DepartureTransferDocument): MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.updateDocument04(request).enqueue(object: Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Log.e("CAPTURE_RESPONSE", "ResultCode: " + response.code())
                Log.e("CAPTURE_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("CAPTURE_ERROR", ""+t.message)
                mResponse.value = ServerResponse(99, "Error de Conexion ${t.message}")
            }

        })

        return mResponse
    }

}