package com.adelnor.adeladmin.ui.activities.editors.property.entries.epr

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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.api.*
import com.adelnor.adeladmin.model.db.ItemExtended
import com.adelnor.adeladmin.model.db.OutItemExtended
import com.adelnor.adeladmin.model.db.PurchaseReturnItem
import com.adelnor.adeladmin.ui.adapters.CaptureAdapter
import com.adelnor.adeladmin.ui.adapters.OutletCaptureAdapter
import com.adelnor.adeladmin.ui.adapters.PurchaseReturnAdapter
import com.adelnor.adeladmin.utils.CommonTools
import com.adelnor.adeladmin.utils.ItemSelectedListener
import com.google.gson.Gson
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*

class EntryByPurchaseReturnActivity : AppCompatActivity() {

    private var viewModel = EntryByPurchaseReturnViewModel()
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
    private lateinit var requestInfo: RequestInfo
    private var tools = CommonTools()
    private lateinit var warehousesAdapter: ArrayAdapter<WarehouseConfig>
    private lateinit var captureAdapter: PurchaseReturnAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var documentData: PurchaseReturnDocument

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_by_purchase_return)
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
        etFolio.hint = "Folio Solicitud"

        setupView()

        btSave.setOnClickListener {
            if(etFolio.text.isNotEmpty() && !captureAdapter.items!!.isEmpty()){
                etFolio.error = null
                loadingDialog.show()
                documentData.user = viewModel.getUser()
                documentData.macAddress = tools.getMacAddress(this)
                if(documentData.captureFolio > 0) updateDocument()
                else saveDocument()
            }else etFolio.error = "Campo Requerido!"
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

    val itemTouchHelper: ItemTouchHelper =  ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            confirmItemDelete(captureAdapter.items!![viewHolder.adapterPosition]!!)
        }
    })

    fun loadSavedDocument(v: View){
        var inputDialog = tools.showFolioInput(this)
        inputDialog.show()
        val etFolio = inputDialog.findViewById<EditText>(R.id.edit_folio)
        val btLoad = inputDialog.findViewById<Button>(R.id.button_load)
        var warehouseId = warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId
        btLoad!!.setOnClickListener{
            if(etFolio!!.text.isNotEmpty()){
                loadingDialog.show()
                tools.hideKeyBoard(this)
                etFolio.error = null
                viewModel.loadSavedDocumentData(etFolio.text.toString().toInt(), warehouseId)
                viewModel.getSavedDocumentData().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    if(response!=null && response.captureFolio>0){
                        v.visibility = View.INVISIBLE
                        btDelete.visibility = View.VISIBLE
                        viewModel.addAllCapturedItems(response.productsList)
                        documentData = response
                        documentData.productsList = ArrayList()
                        this.etFolio.setText("${documentData.requestID}")
                        loadCustomer("${documentData.requestID}")
                        this.etScanBox.requestFocus()
                    } else tools.showMessageDialog(this, "Error", "Folio no encontrado!")
                })
                inputDialog.dismiss()
            }else etFolio.error = "Campo Requerido!"
        }
    }

    private fun setupView(){
        loadingDialog.show()
        viewModel.deleteCapture()
        btDelete.visibility = View.INVISIBLE
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.setHasFixedSize(true)
        captureAdapter = PurchaseReturnAdapter(itemListener)
        rvItems.adapter = captureAdapter
        rvItems.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(rvItems)

        documentData = PurchaseReturnDocument()

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
                    var tmpText = tools.cleanScannerReaderV1(s.toString())
                    Log.e("READED QR", "" + tmpText)
                    processProductRead(tmpText)
                    etScanBox.text = null
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        etFolio.setOnKeyListener{ _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                loadingDialog.show()
                loadCustomer(etFolio.text.toString())
                true
            }
            false
        }
    }

    private fun loadCustomer(folio: String){
        viewModel.loadRequestInfo(
            folio.toInt(),
            warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId)
        viewModel.getRequestInfo().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response!=null && response.customerId>0){
                etFolio.isEnabled = false
                tools.hideKeyBoard(this)
                requestInfo = response
                documentData.customerId = response.customerId
                tvCustomerName.text = requestInfo.customerName
                tvRFC.text = requestInfo.customerRFC
            }else tools.showMessageDialog(this, "Atención!", "Folio de solicitud NO encontrado!")
        })
    }

    private fun processProductRead(result: String){
        if(result.isNotEmpty()){
            var data = result.split("|")
            if(data.size>1){
                viewModel.loadItemDetails(data[0].trim().toInt(), data[1].trim().toInt(), data[2].trim().toInt(), data[3].trim().toFloat())
                viewModel.getItemDetails().observe(this, Observer{ response ->
                    loadingDialog.dismiss()
                    tools.hideKeyBoard(this)
                    if(response!=null && response.productId>0) viewModel.addCapturedItem(response)
                    else tools.showMessageDialog(this, "Error", "Producto No Encontrado!")
                })
            }
        }
    }

    private fun saveDocument(){
        documentData.requestID = etFolio.text.toString().toInt()
        documentData.productsList.addAll(Realm.getDefaultInstance().copyFromRealm(captureAdapter.items!!))
        Log.e("SAVE", "" +  Gson().toJson(documentData))
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
        documentData.productsList.addAll(Realm.getDefaultInstance().copyFromRealm(captureAdapter.items!!))
        Log.e("UPDATE", "" +  Gson().toJson(documentData))
        viewModel.updateInServer(documentData)
        viewModel.getUpdateServerResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response.code == 0){
                viewModel.deleteCapture()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    private fun showDetailsDialog(item: PurchaseReturnItem){
        tools.getPurchaseReturnItemDetails(item, this).show()
    }

    private fun confirmItemDelete(item: PurchaseReturnItem){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_item_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, which ->
            viewModel.deleteItem(item)
            dialog.dismiss()
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, which ->
            captureAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showDocumentDeleteConfirm(){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, _ ->
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

        builder.setNeutralButton(R.string.btn_cancel) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

}