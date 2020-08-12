package com.adelnor.adeladmin.ui.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.adelnor.adeladmin.MainActivity
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.api.LoginResponse
import com.adelnor.adeladmin.model.db.Company
import com.adelnor.adeladmin.model.db.SessionData
import com.adelnor.adeladmin.model.repositories.CloudRepository
import com.adelnor.adeladmin.model.repositories.RealmRepository
import com.adelnor.adeladmin.utils.CommonTools

class LoginActivity : AppCompatActivity() {

    private lateinit var etUser: EditText
    private lateinit var etPassword: EditText
    private lateinit var spServers: Spinner
    private lateinit var companiesAdapter: ArrayAdapter<Company>
    private var cloudRepository = CloudRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etUser = findViewById(R.id.edit_user)
        etPassword = findViewById(R.id.edit_password)
        spServers = findViewById(R.id.spinner_server)
        val tvVersion = findViewById<TextView>(R.id.text_version)
        tvVersion.text = "Version: ${CommonTools().getAppVersion(this)}"

        if(RealmRepository().selectSessionData()!=null){
            this.finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun login(v: View) {
        etUser.error = if (etUser.text.isNullOrEmpty()) "Campo requerido!" else null
        etPassword.error = if (etPassword.text.isNullOrEmpty()) "Campo requerido!" else null

        if (etUser.text.isNotEmpty() && etPassword.text.isNotEmpty())
            loginUser(etUser.text.toString(), etPassword.text.toString())
    }

    private fun loginUser(user: String, pass: String){
        cloudRepository.userLogin(user, pass).observe(this, Observer { response ->
            var sessionData = SessionData(response.user, response.fullName, Company(), spServers.selectedItem.toString())
            loadEnterprises(sessionData)
        })
    }

    private fun loadEnterprises(sessionData: SessionData){
        cloudRepository.loadCompanies(sessionData.user).observe(this, Observer{ response ->
            if(response.size>1){
                companiesAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, response)
                showCompanyConfirm(sessionData)
            }else{
                sessionData.company = response.get(0)
                startApp(sessionData)
            }
        })
    }

    private fun showCompanyConfirm(sessionData: SessionData){
        var companySelectorDialog = Dialog(this)

        var view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_company, null)
        var spCompanies = view.findViewById<Spinner>(R.id.spinner_company_selector)
        spCompanies.adapter = companiesAdapter
        view.findViewById<Button>(R.id.button_accept_company).setOnClickListener {
            companySelectorDialog.dismiss()
            var position = spCompanies.selectedItemPosition
            sessionData.company = companiesAdapter.getItem(position)!!
            startApp(sessionData)
        }
        view.findViewById<Button>(R.id.button_cancel).setOnClickListener{
            companySelectorDialog.dismiss()
        }

        companySelectorDialog.setContentView(view)
        companySelectorDialog.setCancelable(false)
        companySelectorDialog.show()
    }

    private fun startApp(sessionData: SessionData){
        RealmRepository().insertSessionData(sessionData)
        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

}