package com.adelnor.adeladmin.ui.activities.editors.property.departures.dcd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.db.OutItemExtended
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import io.realm.RealmResults

class DepartureByCustomerDeliveryViewModel: ViewModel() {

    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var documentData: MutableLiveData<DepartureDocument>
    private lateinit var savedDocumentData: MutableLiveData<DepartureDocument>
    //private lateinit var itemDetail: MutableLiveData<OutItemExtended>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadSavedDocumentData(documentFolio: Int, warehouseId: Int){
         savedDocumentData = propertyRepository.loadSavedDocument05(documentFolio, warehouseId)
    }

    fun loadDocumentData(invoiceId: Int, warehouseId: Int){
        documentData = propertyRepository.loadDocument05(invoiceId, warehouseId)
    }

    /*fun loadItemDetails(qrCode: Int, productId: Int, presentationId: Int, quantity: Float) {
        itemDetail = propertyRepository.getProductDetails05(qrCode, productId, presentationId, quantity)
    }*/

    fun saveToServer(captureRequest: DepartureDocument){
        saveServerResponse = propertyRepository.saveDocument05(captureRequest)
    }

    fun updateInServer(captureRequest: DepartureDocument) {
        updateServerResponse = propertyRepository.updateDocument05(captureRequest)
    }

    fun deleteInServer(documentFolio: Int, warehouseId: Int, macAddress: String) {
        deleteServerResponse = propertyRepository.cancelDocument05(documentFolio, warehouseId, getUser(), macAddress)
    }

    fun getBranchConfig() = branchConfig

    fun getDocumentData() = documentData

    fun getSavedDocumentData() = savedDocumentData

    //fun getItemDetails() = itemDetail

    fun getSaveServerResponse() = saveServerResponse

    fun getUpdateServerResponse() = updateServerResponse

    fun getDeleteServerResponse() = deleteServerResponse

    fun getUser() = sessionData.user

    fun addCapturedItem(item: OutItemExtended){
        realmRepository.insertCapturedOutItem(item)
    }

    fun addAllCapturedItems(items: List<OutItemExtended>){
        realmRepository.insertCapturedOutItems(items)
    }

    fun updateReadState(idQR: Int) = realmRepository.updateItemRead(idQR)
    /*fun deleteItem(item: OutItemExtended){
        realmRepository.deleteCaptureOutItem(item)
    }*/

    fun deleteCapture(){
        realmRepository.deleteOutCapture()
    }

    fun subscribeToCartItems(): LiveData<RealmResults<OutItemExtended>> {
        return realmRepository.selectCurrentOutCapture()
    }




}