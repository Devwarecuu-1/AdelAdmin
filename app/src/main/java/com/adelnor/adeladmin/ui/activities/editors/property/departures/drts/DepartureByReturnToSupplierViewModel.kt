package com.adelnor.adeladmin.ui.activities.editors.property.departures.drts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.ConsignmentCloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import io.realm.RealmResults

class DepartureByReturnToSupplierViewModel {

    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private lateinit var itemDetail: MutableLiveData<ItemExtended>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var suppliersList: MutableLiveData<List<Supplier>>
    private lateinit var savedDocument: MutableLiveData<SupplierReturnDocument>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadSuppliers() {
        suppliersList = cloudRepository.loadProvidersList()
    }

    fun loadSavedDocument(invoiceId: Int, warehouseIntId: Int) {
        savedDocument = propertyRepository.loadSavedDocument08(invoiceId, warehouseIntId)
    }

    fun loadItemDetails(idQR: Int, productId: Int, presentationId: Int, quantity: Float) {
        itemDetail = propertyRepository.getProductDetails08(idQR, productId, presentationId, quantity)
    }

    fun saveToServer(documentRequest: SupplierReturnDocument){
        saveServerResponse = propertyRepository.saveDocument08(documentRequest)
    }

    fun updateInServer(documentRequest: SupplierReturnDocument) {
        updateServerResponse = propertyRepository.updateDocument08(documentRequest)
    }

    fun deleteInServer(captureFolio: Int, warehouseIntId: Int) {
        deleteServerResponse = propertyRepository.cancelDocument08(captureFolio, warehouseIntId)
    }

    fun getBranchConfig() = branchConfig

    fun getSuppliersList() = suppliersList

    fun getItemDetails() = itemDetail

    fun getSaveServerResponse() = saveServerResponse

    fun getUpdateServerResponse() = updateServerResponse

    fun getDeleteServerResponse() = deleteServerResponse

    fun getSavedDocument() = savedDocument

    fun getCompany() = sessionData.company!!.companyId

    fun addCapturedItem(item: ItemExtended){
        realmRepository.insertCapturedEntryItem(item)
    }

    fun addAllCapturedItems(items: List<ItemExtended>){
        realmRepository.insertCapturedEntryItems(items)
    }

    fun deleteItem(item: ItemExtended){
        realmRepository.deleteCaptureEntryItem(item)
    }

    fun subscribeToCartItems(): LiveData<RealmResults<ItemExtended>> {
        return realmRepository.selectCurrentEntryCapture()
    }

    fun deleteDocument(){
        realmRepository.deleteEntryCapture()
    }
}