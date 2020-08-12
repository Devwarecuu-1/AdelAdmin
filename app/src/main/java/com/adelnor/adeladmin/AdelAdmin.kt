package com.adelnor.adeladmin

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class AdelAdmin : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        var config = RealmConfiguration.Builder()
            .name("AdelAdmin")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
        Realm.getDefaultInstance()
    }
}