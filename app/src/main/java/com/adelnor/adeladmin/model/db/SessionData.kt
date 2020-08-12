package com.adelnor.adeladmin.model.db

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SessionData(
    @PrimaryKey var user: String = "",
    var fullName: String = "",
    var company: Company? = null,
    var mainUrl: String = ""
): RealmObject()