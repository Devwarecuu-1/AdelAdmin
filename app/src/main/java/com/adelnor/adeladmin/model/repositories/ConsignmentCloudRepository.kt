package com.adelnor.adeladmin.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.utils.AppConstants
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class ConsignmentCloudRepository {

    private var cloudApi: ConsignmentAPI

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
            .create(ConsignmentAPI::class.java)
    }

    //==============================================================================================
    // ENTRADA POR CONSIGNACION A PROVEEDOR
    //==============================================================================================
    //region
    fun getProductDetails01(productId: Int, presentationId: Int, quantity: Float): MutableLiveData<ItemExtended> {
        var mResponse = MutableLiveData<ItemExtended>()
        cloudApi.loadProductDetails01(productId, presentationId, quantity).enqueue(object:
            Callback<ItemExtended> {
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

    fun validateFolio01(purchaseId: Int): MutableLiveData<Supplier> {
        var mResponse = MutableLiveData<Supplier>()
        cloudApi.validateFolio01(purchaseId).enqueue(object: Callback<Supplier> {
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

    fun loadSavedDocument01(invoiceId: Int, warehouseIntId: Int): MutableLiveData<SupplierConsignmentDocument> {
        var mResponse = MutableLiveData<SupplierConsignmentDocument>()
        cloudApi.loadSavedDocument01(invoiceId, warehouseIntId).enqueue(object: Callback<SupplierConsignmentDocument> {
            override fun onResponse(call: Call<SupplierConsignmentDocument>, response: Response<SupplierConsignmentDocument>) {
                Log.e("READ_DOCUMENT_RESPONSE", "ResultCode: " + response.code())
                Log.e("READ_DOCUMENT_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<SupplierConsignmentDocument>, t: Throwable) {
                Log.e("READ_CAPTURE_ERROR", ""+t.message)
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun saveDocument01(request: SupplierConsignmentDocument): MutableLiveData<ServerResponse> {
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

    fun updateDocument01(request: SupplierConsignmentDocument): MutableLiveData<ServerResponse> {
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

    fun cancelDocument01(documentFolio: Int, warehouseIntId: Int, user: String, macAddress: String)
            : MutableLiveData<ServerResponse> {
        var mResponse = MutableLiveData<ServerResponse>()
        cloudApi.cancelDocument01(documentFolio, warehouseIntId, user, macAddress).enqueue(object:
            Callback<ServerResponse> {
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

}