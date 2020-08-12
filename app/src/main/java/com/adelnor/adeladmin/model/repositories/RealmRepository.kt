package com.adelnor.adeladmin.model.repositories

import androidx.lifecycle.LiveData
import com.adelnor.adeladmin.model.db.*
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import io.realm.kotlin.where

class RealmRepository {

    private var realm: Realm = Realm.getDefaultInstance()

    fun insertSessionData(sessionData: SessionData) {
        realm.beginTransaction()
        realm.insertOrUpdate(sessionData)
        realm.commitTransaction()
    }

    fun selectSessionData(): SessionData? {
        realm.beginTransaction()
        var sessionData = realm.where<SessionData>().findFirst()
        realm.commitTransaction()
        return sessionData
    }

    fun deleteSessionData() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }


    // ---------------------------------------------------------------------------------------------
    // ENTRIES
    // ---------------------------------------------------------------------------------------------
    //region
    fun selectCurrentEntryCapture(): LiveData<RealmResults<ItemExtended>>{
        realm.beginTransaction()
        var items = realm.where<ItemExtended>().findAllAsync().asLiveData()
        realm.commitTransaction()
        return items
    }

    fun insertCapturedEntryItem(item: ItemExtended){
        realm.beginTransaction()
        realm.insert(item)
        realm.commitTransaction()
    }

    fun insertCapturedEntryItems(items: List<ItemExtended>){
        realm.beginTransaction()
        realm.insert(items)
        realm.commitTransaction()
    }

    fun deleteCaptureEntryItem(item: ItemExtended){
        realm.beginTransaction()
        item.deleteFromRealm()
        realm.commitTransaction()
    }

    fun deleteEntryCapture(){
        realm.beginTransaction()
        realm.where<ItemExtended>()
            .findAll()?.deleteAllFromRealm()
        realm.commitTransaction()
    }
    //endregion

    // ---------------------------------------------------------------------------------------------
    // 3 ENTRADA POR DEVOLUCION A CLIENTE
    // ---------------------------------------------------------------------------------------------
    //region
    fun selectRCurrentEntryCapture(): LiveData<RealmResults<PurchaseReturnItem>>{
        realm.beginTransaction()
        var items = realm.where<PurchaseReturnItem>().findAllAsync().asLiveData()
        realm.commitTransaction()
        return items
    }

    fun insertRCapturedEntryItem(item: PurchaseReturnItem){
        realm.beginTransaction()
        realm.insert(item)
        realm.commitTransaction()
    }

    fun insertRCapturedEntryItems(items: List<PurchaseReturnItem>){
        realm.beginTransaction()
        realm.insert(items)
        realm.commitTransaction()
    }

    fun deleteRCaptureEntryItem(item: PurchaseReturnItem){
        realm.beginTransaction()
        item.deleteFromRealm()
        realm.commitTransaction()
    }

    fun deleteREntryCapture(){
        realm.beginTransaction()
        realm.where<PurchaseReturnItem>()
            .findAll()?.deleteAllFromRealm()
        realm.commitTransaction()
    }
    //endregion
    //----------------------------------------------------------------------------------------------
    // Pantalla 04 Salida Transferencia Almacen
    //----------------------------------------------------------------------------------------------
    fun insertCapturedOutItemsTransfer(items: List<DeapTransferItemPost>){
        realm.beginTransaction()
        realm.insert(items)
        realm.commitTransaction()
    }

    fun deleteDeapTransferItem(item: DeapTransferItemPost){
        realm.beginTransaction()
        item.deleteFromRealm()
        realm.commitTransaction()
    }

    fun insertCapturedOutItemTransfer(item: DeapTransferItem){
        realm.beginTransaction()
        realm.insert(item)
        realm.commitTransaction()
    }
    //endregion

    // ---------------------------------------------------------------------------------------------
    // 07 - SALIDA POR TRANSFERENCIA
    // ---------------------------------------------------------------------------------------------
    //region
    fun selectDepWarehouseTransDocument(): LiveData<RealmResults<DepartureWarehouseTransferItem>>{
        realm.beginTransaction()
        var items = realm.where<DepartureWarehouseTransferItem>().findAllAsync().asLiveData()
        realm.commitTransaction()
        return items
    }

    fun insertDepWarehouseTransItem(item: DepartureWarehouseTransferItem){
        realm.beginTransaction()
        realm.insert(item)
        realm.commitTransaction()
    }

    fun insertDepWarehouseTransItems(items: List<DepartureWarehouseTransferItem>){
        realm.beginTransaction()
        realm.insert(items)
        realm.commitTransaction()
    }

    fun deleteDepWarehouseTransDocument(){
        realm.beginTransaction()
        realm.where<DepartureWarehouseTransferItem>()
            .findAll()?.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun deleteDepWarehouseTransDocumentItem(item: DepartureWarehouseTransferItem){
        realm.beginTransaction()
        item.deleteFromRealm()
        realm.commitTransaction()
    }
    //endregion

    // ---------------------------------------------------------------------------------------------
    // 13 - SALIDA POR REETIQUETADO POR PROVEEDOR
    // ---------------------------------------------------------------------------------------------
    //region
    fun selectWarehouseTransDocument(): LiveData<RealmResults<WarehouseTransferItem>>{
        realm.beginTransaction()
        var items = realm.where<WarehouseTransferItem>().findAllAsync().asLiveData()
        realm.commitTransaction()
        return items
    }

    fun insertWarehouseTransItem(item: WarehouseTransferItem){
        realm.beginTransaction()
        realm.insert(item)
        realm.commitTransaction()
    }

    fun insertWarehouseTransItems(items: List<WarehouseTransferItem>){
        realm.beginTransaction()
        realm.insert(items)
        realm.commitTransaction()
    }

    fun deleteWarehouseTransDocument(){
        realm.beginTransaction()
        realm.where<WarehouseTransferItem>()
            .findAll()?.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun deleteWarehouseTransDocumentItem(item: WarehouseTransferItem){
        realm.beginTransaction()
        item.deleteFromRealm()
        realm.commitTransaction()
    }
    //endregion

    // ---------------------------------------------------------------------------------------------
    // DEPARTURES
    // ---------------------------------------------------------------------------------------------
    //region
    fun selectCurrentOutCapture(): LiveData<RealmResults<OutItemExtended>>{
        realm.beginTransaction()
        var items = realm.where<OutItemExtended>().findAllAsync().asLiveData()
        realm.commitTransaction()
        return items
    }

    fun updateItemRead(idQR: Int): Boolean{
        realm.beginTransaction()
        var item = realm.where<OutItemExtended>()
            .equalTo("qrId", idQR)
            .findFirst()
        if(item!=null) item.read = true
        realm.commitTransaction()
        return item!=null
    }

    fun insertCapturedOutItem(item: OutItemExtended){
        realm.beginTransaction()
        realm.insert(item)
        realm.commitTransaction()
    }

    fun insertCapturedOutItems(items: List<OutItemExtended>){
        realm.beginTransaction()
        realm.insert(items)
        realm.commitTransaction()
    }

    fun deleteCaptureOutItem(item: OutItemExtended){
        realm.beginTransaction()
        item.deleteFromRealm()
        realm.commitTransaction()
    }

    fun deleteOutCapture(){
        realm.beginTransaction()
        realm.where<OutItemExtended>()
            .findAll()?.deleteAllFromRealm()
        realm.commitTransaction()
    }
    //endregion

    // ---------------------------------------------------------------------------------------------
    // 13 - SALIDA POR REETIQUETADO POR PROVEEDOR
    // ---------------------------------------------------------------------------------------------
    //region
    fun selectSupplierReTagDocument(): LiveData<RealmResults<SupplierReTagItem>>{
        realm.beginTransaction()
        var items = realm.where<SupplierReTagItem>().findAllAsync().asLiveData()
        realm.commitTransaction()
        return items
    }

    fun insertSupplierReTagItem(item: SupplierReTagItem){
        realm.beginTransaction()
        realm.insert(item)
        realm.commitTransaction()
    }

    fun insertSupplierReTagItems(items: List<SupplierReTagItem>){
        realm.beginTransaction()
        realm.insert(items)
        realm.commitTransaction()
    }

    fun deleteSupplierReTagDocument(){
        realm.beginTransaction()
        realm.where<SupplierReTagItem>()
            .findAll()?.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun deleteSupplierReTagDocument(item: SupplierReTagItem){
        realm.beginTransaction()
        item.deleteFromRealm()
        realm.commitTransaction()
    }
    //endregion

    fun <T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData(this)

}