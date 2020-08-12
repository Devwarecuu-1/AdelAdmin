package com.adelnor.adeladmin.model.api;

import com.adelnor.adeladmin.model.db.ItemExtended
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmResults

class LoginResponse(
        @SerializedName("usuario") var user: String = "",
        @SerializedName("nombre") var fullName: String = ""
)

class ServerResponse(
        var code: Int = 0,
        var message: String = ""
)

class CustomerInfo(
        @SerializedName("cRFCCliente") var rfc: String = "",
        @SerializedName("nCodCliente") var customerId: Int = 0,
        @SerializedName("cNombre") var name: String = ""
)