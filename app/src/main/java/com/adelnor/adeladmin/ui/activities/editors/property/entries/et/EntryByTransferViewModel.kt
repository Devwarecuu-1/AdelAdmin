package com.adelnor.adeladmin.ui.activities.editors.property.entries.et

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.db.OutItemExtended
import com.adelnor.adeladmin.model.db.WarehouseTransferItem
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import io.realm.RealmResults

class EntryByTransferViewModel {

    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var itemDetail: MutableLiveData<WarehouseTransferItem>
    private lateinit var savedDocumentData: MutableLiveData<WarehouseTransferDocument>
    private lateinit var validateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadItemDetails(qrCode: Int, productId: Int, presentationId: Int, quantity: Float) {
        itemDetail = propertyRepository.getProductDetails02(qrCode, productId, presentationId, quantity)
    }

    fun validateFolio(requestFolio: Long, warehouseId: Int){
        validateServerResponse = propertyRepository.validateFolio02(requestFolio, warehouseId)
    }

    fun loadSavedDocument(invoiceId: Int, warehouseId: Int){
        savedDocumentData = propertyRepository.loadSavedDocument02(invoiceId, warehouseId)
    }

    fun saveToServer(captureRequest: WarehouseTransferDocument){
        saveServerResponse = propertyRepository.saveDocument02(captureRequest)
    }

    fun updateInServer(captureRequest: WarehouseTransferDocument) {
        updateServerResponse = propertyRepository.updateDocument02(captureRequest)
    }

    fun deleteInServer(captureFolio: Int, warehouseId: Int) {
        deleteServerResponse = propertyRepository.cancelDocument02(captureFolio, warehouseId)
    }

    fun getBranchConfig() = branchConfig

    fun getValidFolio() = validateServerResponse

    fun getSavedDocumentData() = savedDocumentData

    fun getItemDetails() = itemDetail

    fun getSaveServerResponse() = saveServerResponse

    fun getUpdateServerResponse() = updateServerResponse

    fun getDeleteServerResponse() = deleteServerResponse

    fun getUser() = sessionData.user

    fun addCapturedItem(item: WarehouseTransferItem){
        realmRepository.insertWarehouseTransItem(item)
    }

    fun addAllCapturedItems(items: List<WarehouseTransferItem>){
        realmRepository.insertWarehouseTransItems(items)
    }

    fun deleteItem(item: WarehouseTransferItem){
        realmRepository.deleteWarehouseTransDocumentItem(item)
    }

    fun deleteCapture(){
        realmRepository.deleteWarehouseTransDocument()
    }

    fun subscribeToCartItems(): LiveData<RealmResults<WarehouseTransferItem>> {
        return realmRepository.selectWarehouseTransDocument()
    }

}