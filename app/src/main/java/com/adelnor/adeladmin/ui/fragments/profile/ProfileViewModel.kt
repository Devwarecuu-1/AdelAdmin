package com.adelnor.adeladmin.ui.fragments.profile

import androidx.lifecycle.ViewModel
import com.adelnor.adeladmin.model.repositories.RealmRepository

class ProfileViewModel : ViewModel() {

    var realm: RealmRepository = RealmRepository()

    fun getSessionData() = realm.selectSessionData()

    fun deleteSession(){
        realm.deleteSessionData()
    }

}