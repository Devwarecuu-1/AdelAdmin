package com.adelnor.adeladmin.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.Company
import com.adelnor.adeladmin.utils.AppConstants.URL_MAIN
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CloudRepository {

    private var cloudApi: CloudAPI

    constructor(){
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
            .create(CloudAPI::class.java)
    }

    fun userLogin(user: String, pass: String): MutableLiveData<LoginResponse>{
        var mResponse = MutableLiveData<LoginResponse>()
        cloudApi.loginUser(user, pass).enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.e("LOGIN_RESPONSE", "ResultCode: " + response.code());
                Log.e("LOGIN_RESPONSE", "" + Gson().toJson(response.body()));
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LOGIN_ERROR", ""+t.message);
                mResponse.value = null
            }

        })

        return mResponse
    }

    fun loadCompanies(user: String): MutableLiveData<List<Company>>{
        var mResponse = MutableLiveData<List<Company>>()
        cloudApi.loadCompanies(user).enqueue(object: Callback<List<Company>>{
            override fun onResponse(call: Call<List<Company>>, response: Response<List<Company>>) {
                Log.e("COMPANIES_RESPONSE", "ResultCode: " + response.code());
                Log.e("COMPANIES_RESPONSE", "" + Gson().toJson(response.body()));
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<List<Company>>, t: Throwable) {
                Log.e("COMPANIES_ERROR", ""+t.message);
                mResponse.value = null
            }
        })

        return mResponse
    }

    fun loadProvidersList(): MutableLiveData<List<Supplier>> {
        var mResponse = MutableLiveData<List<Supplier>>()
        cloudApi.loadProvidersList().enqueue(object: Callback<List<Supplier>> {
            override fun onResponse(call: Call<List<Supplier>>, response: Response<List<Supplier>>) {
                Log.e("PROVIDERS_RESPONSE", "ResultCode: " + response.code())
                Log.e("PROVIDERS_RESPONSE", "" + Gson().toJson(response.body()))
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<List<Supplier>>, t: Throwable) {
                Log.e("PROVIDERS_ERROR", ""+t.message)
                mResponse.value = ArrayList()
            }

        })

        return mResponse
    }

    fun loadBranchConfig(user: String): MutableLiveData<BranchInfo>{
        var mResponse = MutableLiveData<BranchInfo>()
        cloudApi.loadBranchConfig(user).enqueue(object: Callback<BranchInfo>{
            override fun onResponse(call: Call<BranchInfo>, response: Response<BranchInfo>) {
                Log.e("BRANCH_INFO_RESPONSE", "ResultCode: " + response.code());
                Log.e("BRANCH_INFO_RESPONSE", "" + Gson().toJson(response.body()));
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<BranchInfo>, t: Throwable) {
                Log.e("BRANCH_INFO_ERROR", ""+t.cause?.message);
                Log.e("BRANCH_INFO_ERROR", ""+t.message);
                mResponse.value = null
            }

        })

        return mResponse
    }

    fun loadCustomerInfo(folio: Int): MutableLiveData<CustomerInfo>{
        var mResponse = MutableLiveData<CustomerInfo>()
        cloudApi.loadCustomerInfo(folio).enqueue(object: Callback<CustomerInfo>{
            override fun onResponse(call: Call<CustomerInfo>, response: Response<CustomerInfo>) {
                Log.e("CUSTOMER_INFO_RESPONSE", "ResultCode: " + response.code());
                Log.e("CUSTOMER_INFO_RESPONSE", "" + Gson().toJson(response.body()));
                mResponse.value = response.body()
            }

            override fun onFailure(call: Call<CustomerInfo>, t: Throwable) {
                Log.e("BRANCH_INFO_ERROR", ""+t.cause?.message);
                Log.e("BRANCH_INFO_ERROR", ""+t.message);
                mResponse.value = null
            }
        })
        return mResponse
    }


}