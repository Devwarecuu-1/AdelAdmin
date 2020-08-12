package com.adelnor.adeladmin.model.db

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class DomiDat (
    @SerializedName("code") var code: Int = 0,
    @SerializedName("message") var message: String = ""
): RealmObject()