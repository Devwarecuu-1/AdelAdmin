package com.adelnor.adeladmin.ui.activities.editors.property.departures.drs

import android.content.Context
import android.net.MacAddress
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adelnor.adeladmin.model.api.BranchInfo
import com.adelnor.adeladmin.model.api.DepartureDocument
import com.adelnor.adeladmin.model.api.ServerResponse
import com.adelnor.adeladmin.model.api.SupplierReTagDocument
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.db.OutItemExtended
import com.adelnor.adeladmin.model.db.SupplierReTagItem
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import io.realm.RealmResults
import retrofit2.http.Query

class DepartureByRTaggingBySupplierViewModel: ViewModel() {

    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var productDetails: MutableLiveData<SupplierReTagItem>
    private lateinit var document: MutableLiveData<SupplierReTagDocument>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadProductDetails(qrCode: Int, prodId: Int, presentationId: Int, quantity: Int) {
        productDetails = propertyRepository.getProductDetails13(qrCode, prodId, presentationId, quantity)
    }

    fun loadSavedDocument(invoiceId: Int, warehouseId: Int){
        document = propertyRepository.loadSavedDocument13(invoiceId, warehouseId)
    }

    fun saveDocument(document: SupplierReTagDocument){
        saveServerResponse = propertyRepository.saveDocument13(document)
    }

    fun updateDocument(document: SupplierReTagDocument){
        updateServerResponse = propertyRepository.updateDocument13(document)
    }

    fun deleteServerDocument(documentFolio: Int, warehouseId: Int){
        deleteServerResponse = propertyRepository.cancelDocument13(documentFolio, warehouseId)
    }

    fun getBranchConfig() = branchConfig

    fun getProductDetails() = productDetails

    fun getSavedDocument() = document

    fun getSaveResponse() = saveServerResponse

    fun getUpdateResponse() = updateServerResponse

    fun getDeleteResponse() = deleteServerResponse

    fun getUser() = sessionData.user

    fun saveDocumentItem(item: SupplierReTagItem){
        realmRepository.insertSupplierReTagItem(item)
    }

    fun saveDocumentItems(items: List<SupplierReTagItem>){
        realmRepository.insertSupplierReTagItems(items)
    }

    fun deleteDocumentItem(item: SupplierReTagItem){
        realmRepository.deleteSupplierReTagDocument(item)
    }

    fun deleteDocument(){
        realmRepository.deleteSupplierReTagDocument()
    }

    fun subscribeToItems(): LiveData<RealmResults<SupplierReTagItem>> {
        return realmRepository.selectSupplierReTagDocument()
    }

}