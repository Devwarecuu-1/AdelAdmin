package com.adelnor.adeladmin.ui.activities.editors.adjust

import androidx.lifecycle.MutableLiveData
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.DeapTransferItem
import com.adelnor.adeladmin.model.db.DeapTransferItem2
import com.adelnor.adeladmin.model.db.DeapTransferItemPost
import com.adelnor.adeladmin.model.db.DomiDat
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.PropertyCloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository

class AdjustByMerchandiseArrangementViewModel {
    private var cloudRepository = CloudRepository()
    private var propertyRepository = PropertyCloudRepository()
    private var realmRepository = RealmRepository()
    private var sessionData = realmRepository.selectSessionData()!!
    private lateinit var branchConfig: MutableLiveData<BranchInfo>
    private lateinit var productDetails: MutableLiveData<DeapTransferItem2>
    private lateinit var datoDomicilio: MutableLiveData<DomiDat>
    private lateinit var document: MutableLiveData<MerchandiseArraDocument>
    private lateinit var saveServerResponse: MutableLiveData<ServerResponse>
    private lateinit var updateServerResponse: MutableLiveData<ServerResponse>
    private lateinit var deleteServerResponse: MutableLiveData<ServerResponse>

    fun loadBranchConfig() {
        branchConfig = cloudRepository.loadBranchConfig(sessionData.user)
    }

    fun loadProductDetails(qrCode: Int, prodId: Int, presentationId: Int, quantity: Float) {
        productDetails = propertyRepository.getProductDetails82(qrCode, prodId, quantity, presentationId)
    }
    fun loadDomi(nAlmacenInterno: Int, nDomicilio: String) {
        datoDomicilio = propertyRepository.getDomi82(nAlmacenInterno, nDomicilio)
    }

    fun saveDocument(document: MerchandiseArraDocument){
        saveServerResponse = propertyRepository.saveDocument82(document)
    }



    fun getBranchConfig() = branchConfig

    fun getProductDetails() = productDetails

    fun getDomiDetails() = datoDomicilio

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