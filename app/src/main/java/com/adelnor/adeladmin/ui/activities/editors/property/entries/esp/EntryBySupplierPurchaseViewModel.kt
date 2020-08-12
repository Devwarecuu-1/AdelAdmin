package com.adelnor.adeladmin.ui.activities.editors.property.entries.esp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adelnor.adeladmin.model.api.BranchInfo
import com.adelnor.adeladmin.model.api.EntryDocument
import com.adelnor.adeladmin.model.api.ServerResponse
import com.adelnor.adeladmin.model.api.Supplier
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import io.realm.RealmResults

class EntryBySupplierPurchaseViewModel: ViewModel() {

    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var providersList: MutableLiveData<List<Supplier>>
    private lateinit var itemDetail: MutableLiveData<ItemExtended>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteItemServerResponse: MutableLiveData<ServerResponse>
    private lateinit var prevCapture: MutableLiveData<EntryDocument>
    private lateinit var provider: MutableLiveData<Supplier>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadProviders() {
        providersList = cloudRepository.loadProvidersList()
    }

    fun saveToServer(captureRequest: EntryDocument){
        saveServerResponse = propertyRepository.saveDocument01(captureRequest)
    }

    fun updateInServer(captureRequest: EntryDocument) {
        updateServerResponse = propertyRepository.updateDocument01(captureRequest)
    }

    fun deleteInServer(captureFolio: Int, warehouseIntId: Int) {
        deleteServerResponse = propertyRepository.cancelDocument01(captureFolio, warehouseIntId)
    }

    /*fun deleteItemInServer(productId: Int, presentationId: Int, quantity: Float, lot: String) {
        deleteItemServerResponse = propertyRepository.deleteCapturedItem(productId, presentationId, quantity, lot)
    }*/

    fun loadItemDetails(productId: Int, presentationId: Int, quantity: Float) {
        itemDetail = propertyRepository.getProductDetails01(productId, presentationId, quantity)
    }

    fun loadCaptureProvider(purchaseOrder: Int) {
        provider = propertyRepository.loadProviderByPurchaseCode(purchaseOrder)
    }

    fun loadPrevCapture(captureFolio: Int, warehouseIntId: Int) {
        prevCapture = propertyRepository.loadDocument01(captureFolio, warehouseIntId)
    }

    fun getBranchConfig() = branchConfig

    fun getProvidersList() = providersList

    fun getItemDetails() = itemDetail

    fun getSaveServerResponse() = saveServerResponse

    fun getUpdateServerResponse() = updateServerResponse

    fun getDeleteServerResponse() = deleteServerResponse

    //fun getDeleteItemServerResponse() = deleteItemServerResponse

    fun getCompany() = sessionData.company!!.companyId

    fun getPrevCapture() = prevCapture

    fun getPurchaseOrderProvider() = provider

    fun addCapturedItem(item: ItemExtended){
        realmRepository.insertCapturedEntryItem(item)
    }

    fun addAllCapturedItems(items: List<ItemExtended>){
        realmRepository.insertCapturedEntryItems(items)
    }

    fun deleteItem(item: ItemExtended){
        realmRepository.deleteCaptureEntryItem(item)
    }

    fun deleteCapture(){
        realmRepository.deleteEntryCapture()
    }

    fun subscribeToCartItems(): LiveData<RealmResults<ItemExtended>>{
        return realmRepository.selectCurrentEntryCapture()
    }





}