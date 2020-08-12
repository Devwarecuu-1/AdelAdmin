package com.adelnor.adeladmin.ui.activities.editors.property.departures.dcd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.OutItemExtended
import com.adelnor.adeladmin.ui.adapters.OutletCaptureAdapter
import com.adelnor.adeladmin.utils.CommonTools
import com.adelnor.adeladmin.utils.ItemSelectedListener
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.Realm.getDefaultInstance
import io.realm.RealmResults
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.util.*

class DepartureByCustomerDeliveryActivity : AppCompatActivity() {

    private var viewModel = DepartureByCustomerDeliveryViewModel()
    private lateinit var sToolBar: Toolbar
    private lateinit var rvItems: RecyclerView
    private lateinit var spWarehouses: Spinner
    private lateinit var btSave: Button
    private lateinit var btDelete: Button
    private lateinit var tvTitle: TextView
    private lateinit var tvBranch: TextView
    private lateinit var tvCustomerName: TextView
    private lateinit var tvRFC: TextView
    private lateinit var etScanBox: EditText
    private lateinit var etFolio: EditText
    private lateinit var branchInfo: BranchInfo
    private var tools = CommonTools()
    private lateinit var warehousesAdapter: ArrayAdapter<WarehouseConfig>
    private lateinit var captureAdapter: OutletCaptureAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var documentData: DepartureDocument

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departure_by_customer_delivery)
        sToolBar = findViewById(R.id.toolbar)
        rvItems = findViewById(R.id.recycler_capture)
        spWarehouses = findViewById(R.id.spinner_warehouses)
        btSave = findViewById(R.id.button_capture_save)
        btDelete = findViewById(R.id.button_capture_delete)
        tvBranch = findViewById(R.id.text_branch)
        tvCustomerName = findViewById(R.id.text_customer)
        tvRFC = findViewById(R.id.text_rfc)
        tvTitle = findViewById(R.id.text_title)
        etScanBox = findViewById(R.id.edit_scan_box)
        etFolio = findViewById(R.id.edit_invoice_folio)
        setSupportActionBar(sToolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        tvTitle.text = intent.getStringExtra("menu-item")
        loadingDialog = tools.getLoadingDialog(this)

        setupView()

        btSave.setOnClickListener {
            if(documentData.invoiceId>0){
                loadingDialog.show()
                documentData.user = viewModel.getUser()
                documentData.macAddress = tools.getMacAddress(this)
                documentData.productsList.addAll(getDefaultInstance().copyFromRealm(captureAdapter.items!!))
                if(documentData.captureFolio>0) updateDocument()
                else saveDocument()
            }
        }

        btDelete.setOnClickListener {
            showDocumentDeleteConfirm()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        viewModel.deleteCapture()
        super.onBackPressed()
    }

    var itemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            showDetailsDialog(captureAdapter.items!![index]!!)
        }
    }

    fun loadSavedDocument(v: View){
        var inputDialog = tools.showFolioInput(this)
        inputDialog.show()
        val etFolio = inputDialog.findViewById<EditText>(R.id.edit_folio)
        val btLoad = inputDialog.findViewById<Button>(R.id.button_load)
        btLoad!!.setOnClickListener{
            if(etFolio!!.text.isNotEmpty()){
                loadingDialog.show()
                tools.hideKeyBoard(this)
                etFolio.error = null
                viewModel.loadSavedDocumentData(etFolio.text.toString().toInt(),
                    warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId)
                viewModel.getSavedDocumentData().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    if(response!=null && response.invoiceId>0){
                        this.etScanBox.isEnabled = true
                        v.visibility = View.INVISIBLE
                        btDelete.visibility = View.VISIBLE
                        viewModel.addAllCapturedItems(response.productsList)
                        documentData = response
                        documentData.productsList = ArrayList()
                        this.etFolio.setText("${documentData.invoiceId}")
                        this.etFolio.isEnabled = false

                        tvCustomerName.text = documentData.customerName
                        tvRFC.text = documentData.customerRFC

                        for (i in 0 until warehousesAdapter.count) {
                            if(warehousesAdapter.getItem(i)!!.warehouseId == response.warehouseId){
                                spWarehouses.setSelection(i)
                                spWarehouses.isEnabled = false
                                break
                            }
                        }

                        this.etScanBox.requestFocus()
                    } else tools.showMessageDialog(this, "Error", "Folio no encontrado!")
                })
                inputDialog.dismiss()
            }else etFolio.error = "Campo Requerido!"
        }
    }

    fun setupView(){
        loadingDialog.show()
        viewModel.deleteCapture()
        etScanBox.isEnabled = false
        btDelete.visibility = View.INVISIBLE
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.setHasFixedSize(true)
        captureAdapter = OutletCaptureAdapter(itemListener)
        rvItems.adapter = captureAdapter

        documentData = DepartureDocument()

        viewModel.loadBranchConfig()

        viewModel.getBranchConfig().observe(this, Observer { response ->
            if(response!=null){
                loadingDialog.dismiss()
                branchInfo = response
                tvBranch.text = "${branchInfo.branchId} ${branchInfo.branchName}"
                warehousesAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, branchInfo.warehouses)
                spWarehouses.adapter = warehousesAdapter
            } else tools.showMessageDialog(this, "Error", "Error cargando datos!")
        })

        viewModel.subscribeToCartItems().observe(this, Observer{ results ->
            captureAdapter.items = results
            captureAdapter.notifyDataSetChanged()
        })

        spWarehouses.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                documentData.warehouseId = (spWarehouses.selectedItem as WarehouseConfig).warehouseId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etScanBox.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()) {
                    var tmpId = tools.cleanScannerReaderV1(s.toString())
                    Log.e("READED QR", "" + tmpId.split("|")[0])
                    processProductRead(tmpId.split("|")[0].toInt())
                    etScanBox.text = null
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        etFolio.setOnKeyListener{ _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                loadingDialog.show()
                loadDocument(etFolio.text.toString())
                true
            }
            false
        }
    }

    private fun loadDocument(folio: String){
        viewModel.loadDocumentData(
            folio.toInt(),
            warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId)
        viewModel.getDocumentData().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response!=null && response.invoiceId>0){
                tools.hideKeyBoard(this)
                this.etFolio.isEnabled = false
                this.etScanBox.isEnabled = true
                documentData = response
                tvCustomerName.text = documentData.customerName
                tvRFC.text = documentData.customerRFC
                viewModel.addAllCapturedItems(documentData.productsList)
                documentData.productsList = ArrayList()
            }else{
                this.etFolio.text = null
                tools.showMessageDialog(this, "Error de Folio", "Folio NO encontrado!")
            }
        })
    }

    /*private fun processProductRead(result: String){
        if(result.isNotEmpty()){
            var data = result.split("|")
            if(data.size>1){
                viewModel.loadItemDetails(data[0].trim().toInt(), data[1].trim().toInt(), data[4].trim().toInt(), data[5].trim().toFloat())
                viewModel.getItemDetails().observe(this, Observer{ response ->
                    tools.hideKeyBoard(this)
                    if(response!=null){
                        response.lot = data[7].trim()
                        response.expirationDate = data[8].trim()
                        response.elaborationDate = data[9].trim()
                        val expirationDate = SimpleDateFormat("yyyy/MM/dd").parse(data[8].trim().replace("-", "/"))
                        var expTerm = ((expirationDate.getTime()- Date().getTime())/86400000)/30

                        if(expTerm>11) viewModel.addCapturedItem(response)
                        else confirmAddProduct(response)

                    } else tools.showAlertDialog(this, "Error", "Producto No Encontrado!")
                })
            }
        }
    }*/

    private fun processProductRead(idQR: Int){
        if(!viewModel.updateReadState(idQR))
            tools.showMessageDialog(this, "Error!", "Producto NO Encontrado!")
    }

    private fun saveDocument(){
        Log.e("JSON_SAVE", ""+Gson().toJson(documentData))
        viewModel.saveToServer(documentData)
        viewModel.getSaveServerResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response.code == 0){
                viewModel.deleteCapture()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    private fun updateDocument(){
        Log.e("JSON_UPDATE", ""+Gson().toJson(documentData))
        viewModel.updateInServer(documentData)
        viewModel.getUpdateServerResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response.code == 0){
                viewModel.deleteCapture()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    private fun showDetailsDialog(item: OutItemExtended){
        tools.getOutProductDetailsDialog(item,this).show()
    }

    private fun showDocumentDeleteConfirm(){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, which ->
            loadingDialog.show()
            viewModel.deleteInServer(documentData.captureFolio,
                warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId,
                tools.getMacAddress(this))
            viewModel.getDeleteServerResponse().observe(this, Observer { response ->
                dialog.dismiss()
                loadingDialog.dismiss()
                if(response!=null && response.code == 0) tools.showEndMessage(response.message, this)
                else tools.showMessageDialog(this, "Atencion", "Error al Cancelar Documento!")
            })
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

}