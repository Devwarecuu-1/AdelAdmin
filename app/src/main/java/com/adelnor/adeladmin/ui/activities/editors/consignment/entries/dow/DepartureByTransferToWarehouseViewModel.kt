package com.adelnor.adeladmin.ui.activities.editors.consignment.entries.dow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adelnor.adeladmin.model.api.BranchInfo
import com.adelnor.adeladmin.model.api.DepartureTransferDocument
import com.adelnor.adeladmin.model.api.ServerResponse
import com.adelnor.adeladmin.model.db.DeapTransferItem
import com.adelnor.adeladmin.model.db.DeapTransferItemPost
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository

class DepartureByTransferToWarehouseViewModel: ViewModel() {
    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var productDetails: MutableLiveData<DeapTransferItem>
    private lateinit var document: MutableLiveData<DepartureTransferDocument>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadProductDetails(qrCode: Int, prodId: Int, presentationId: Int, quantity: Float, folEntrega: Int) {
        productDetails = propertyRepository.getProductDetails020(qrCode, prodId, quantity, presentationId,  folEntrega)
    }

    fun loadSavedDocument(nidqr: Int, warehouseId: Int){
        document = propertyRepository.loadDocument020(nidqr, warehouseId)
    }

    fun saveDocument(document: DepartureTransferDocument){
        saveServerResponse = propertyRepository.saveDocument020(document)
    }

    fun updateDocument(document: DepartureTransferDocument){
        updateServerResponse = propertyRepository.updateDocument020(document)
    }

    fun deleteServerDocument(documentFolio: Int, warehouseId: Int, user: String, macAddress: String){
        deleteServerResponse = propertyRepository.cancelDocument020(documentFolio, warehouseId, user, macAddress)
    }

    fun getBranchConfig() = branchConfig

    fun getProductDetails() = productDetails

    fun getSavedDocument() = document

    fun getSaveResponse() = saveServerResponse

    fun getUpdateResponse() = updateServerResponse

    fun getDeleteResponse() = deleteServerResponse

    fun getUser() = sessionData.user


    fun saveDocumentItems(items: List<DeapTransferItemPost>){
        realmRepository.insertCapturedOutItemsTransfer(items)
    }

    fun deleteDocument(){
        realmRepository.deleteOutCapture()
    }

    fun deleteDocumentItem(item: DeapTransferItemPost){
        realmRepository.deleteDeapTransferItem(item)
    }
    fun saveDocumentItem(item: DeapTransferItem){
        realmRepository.insertCapturedOutItemTransfer(item)
    }

    fun addCapturedItem(item: DeapTransferItem){
        realmRepository.insertCapturedOutItemTransfer(item)
    }


}