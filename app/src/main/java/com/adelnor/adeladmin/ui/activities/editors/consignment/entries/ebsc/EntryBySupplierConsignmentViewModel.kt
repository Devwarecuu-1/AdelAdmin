package com.adelnor.adeladmin.ui.activities.editors.consignment.entries.ebsc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.ConsignmentCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import io.realm.RealmResults

class EntryBySupplierConsignmentViewModel: ViewModel() {

    private var cloudRepository = CloudRepository()
    private var consignmentRepository = ConsignmentCloudRepository()
    private var realmRepository = RealmRepository()
    private lateinit var itemDetail: MutableLiveData<ItemExtended>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var suppliersList: MutableLiveData<List<Supplier>>
    private lateinit var supplier: MutableLiveData<Supplier>
    //private lateinit var document: MutableLiveData<SupplierConsignmentDocument>
    private lateinit var savedDocument: MutableLiveData<SupplierConsignmentDocument>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadSuppliers() {
        suppliersList = cloudRepository.loadProvidersList()
    }

    fun validateFolio(purchaseOrder: Int) {
        supplier = consignmentRepository.validateFolio01(purchaseOrder)
    }

    fun loadSavedDocument(invoiceId: Int, warehouseIntId: Int) {
        savedDocument = consignmentRepository.loadSavedDocument01(invoiceId, warehouseIntId)
    }

    fun loadItemDetails(productId: Int, presentationId: Int, quantity: Float) {
        itemDetail = consignmentRepository.getProductDetails01(productId, presentationId, quantity)
    }

    fun saveToServer(documentRequest: SupplierConsignmentDocument){
        saveServerResponse = consignmentRepository.saveDocument01(documentRequest)
    }

    fun updateInServer(documentRequest: SupplierConsignmentDocument) {
        updateServerResponse = consignmentRepository.updateDocument01(documentRequest)
    }

    fun deleteInServer(captureFolio: Int, warehouseIntId: Int, macAddress: String) {
        deleteServerResponse = consignmentRepository.cancelDocument01(captureFolio, warehouseIntId, sessionData.user, macAddress)
    }

    fun getBranchConfig() = branchConfig

    fun getSuppliersList() = suppliersList

    fun getFolioValidation() = supplier

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