package com.adelnor.adeladmin.ui.activities.editors.property.entries.epr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.PurchaseReturnItem
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import io.realm.RealmResults

class EntryByPurchaseReturnViewModel {

    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var requestInfo: MutableLiveData<RequestInfo>
    private lateinit var itemDetail: MutableLiveData<PurchaseReturnItem>
    private lateinit var documentData: MutableLiveData<PurchaseReturnDocument>
    private lateinit var savedDocumentData: MutableLiveData<PurchaseReturnDocument>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadRequestInfo(requestFolio: Int, warehouseId: Int) {
        requestInfo = propertyRepository.validateFolio03(requestFolio, warehouseId)
    }

    fun loadItemDetails(qrCode: Int, productId: Int, presentationId: Int, quantity: Float) {
        itemDetail = propertyRepository.getProductDetails03(qrCode, productId, presentationId, quantity)
    }

    fun loadSavedDocumentData(documentFolio: Int, warehouseId: Int){
        savedDocumentData = propertyRepository.loadSavedDocument03(documentFolio, warehouseId)
    }

    fun loadDocumentData(invoiceId: Int, warehouseId: Int){
        documentData = propertyRepository.loadSavedDocument03(invoiceId, warehouseId)
    }

    fun saveToServer(captureRequest: PurchaseReturnDocument){
        saveServerResponse = propertyRepository.saveDocument03(captureRequest)
    }

    fun updateInServer(captureRequest: PurchaseReturnDocument) {
        updateServerResponse = propertyRepository.updateDocument03(captureRequest)
    }

    fun deleteInServer(captureFolio: Int, warehouseId: Int, macAddress: String) {
        deleteServerResponse = propertyRepository.cancelDocument03(captureFolio, warehouseId, getUser(), macAddress)
    }

    fun getBranchConfig() = branchConfig

    fun getRequestInfo() = requestInfo

    fun getSavedDocumentData() = savedDocumentData

    fun getItemDetails() = itemDetail

    fun getSaveServerResponse() = saveServerResponse

    fun getUpdateServerResponse() = updateServerResponse

    fun getDeleteServerResponse() = deleteServerResponse

    fun getUser() = sessionData.user

    fun addCapturedItem(item: PurchaseReturnItem){
        realmRepository.insertRCapturedEntryItem(item)
    }

    fun addAllCapturedItems(items: List<PurchaseReturnItem>){
        realmRepository.insertRCapturedEntryItems(items)
    }

    //fun updateReadState(idQR: Int) = realmRepository.updateItemRead(idQR)
    fun deleteItem(item: PurchaseReturnItem){
        realmRepository.deleteRCaptureEntryItem(item)
    }

    fun deleteCapture(){
        realmRepository.deleteREntryCapture()
    }

    fun subscribeToCartItems(): LiveData<RealmResults<PurchaseReturnItem>> {
        return realmRepository.selectRCurrentEntryCapture()
    }



}