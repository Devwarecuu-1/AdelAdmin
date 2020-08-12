package com.adelnor.adeladmin.ui.activities.editors.property.entries.et

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
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
import com.adelnor.adeladmin.model.db.WarehouseTransferItem
import com.adelnor.adeladmin.ui.adapters.CaptureAdapter
import com.adelnor.adeladmin.ui.adapters.OutletCaptureAdapter
import com.adelnor.adeladmin.ui.adapters.WarehouseTransferAdapter
import com.adelnor.adeladmin.utils.CommonTools
import com.adelnor.adeladmin.utils.ItemSelectedListener
import com.google.gson.Gson
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*

class EntryByTransferActivity : AppCompatActivity() {

    private var viewModel = EntryByTransferViewModel()
    private lateinit var sToolBar: Toolbar
    private lateinit var rvItems: RecyclerView
    private lateinit var spWarehouses: Spinner
    private lateinit var spProvider: Spinner
    private lateinit var btSave: Button
    private lateinit var btDelete: Button
    private lateinit var tvBranch: TextView
    private lateinit var tvSupplierTitle: TextView
    private lateinit var etScanBox: EditText
    private lateinit var etFolio: EditText
    private lateinit var tvTitle: TextView
    private lateinit var branchInfo: BranchInfo
    private lateinit var captureAdapter: WarehouseTransferAdapter
    private lateinit var warehousesAdapter: ArrayAdapter<WarehouseConfig>
    private lateinit var documentData: WarehouseTransferDocument
    private var tools = CommonTools()
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_by_transfer)
        sToolBar = findViewById(R.id.toolbar)
        rvItems = findViewById(R.id.recycler_capture)
        spWarehouses = findViewById(R.id.spinner_warehouses)
        spProvider = findViewById(R.id.spinner_providers)
        btSave = findViewById(R.id.button_capture_save)
        btDelete = findViewById(R.id.button_capture_delete)
        tvBranch = findViewById(R.id.text_branch)
        tvSupplierTitle = findViewById(R.id.text_label_supplier)
        etScanBox = findViewById(R.id.edit_scan_box)
        etFolio = findViewById(R.id.edit_scan_purchase_order)
        tvTitle = findViewById(R.id.text_title)
        setSupportActionBar(sToolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        tvTitle.text = intent.getStringExtra("menu-item")
        loadingDialog = tools.getLoadingDialog(this)
        etFolio.hint = "Folio de Salida"
        etScanBox.isEnabled = false
        spProvider.visibility = GONE
        tvSupplierTitle.visibility = GONE

        setupView()

        btSave.setOnClickListener{
            if(etFolio.text.isNotEmpty()){
                etFolio.error = null
                loadingDialog.show()
                documentData.user = viewModel.getUser()
                documentData.macAddress = tools.getMacAddress(this)
                if(documentData.captureFolio > 0) updateDocument()
                else saveDocument()
            }else etFolio.error = "Campo Requerido!"
        }

        btDelete.setOnClickListener{
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
                viewModel.loadSavedDocument(etFolio.text.toString().toInt(), warehouseId)
                viewModel.getSavedDocumentData().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    if(response!=null && response.departureFolio>0){
                        v.visibility = View.INVISIBLE
                        btDelete.visibility = View.VISIBLE
                        viewModel.addAllCapturedItems(response.productsList)
                        documentData = response
                        documentData.productsList = ArrayList()
                        this.etFolio.setText("${documentData.departureFolio}")
                        this.etFolio.isEnabled = false
                        this.etScanBox.isEnabled = true
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
        btDelete.visibility = View.INVISIBLE
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(rvItems)
        captureAdapter = WarehouseTransferAdapter(itemListener)
        rvItems.adapter = captureAdapter

        documentData = WarehouseTransferDocument()

        viewModel.loadBranchConfig()

        viewModel.getBranchConfig().observe(this, Observer { response ->
            if(response!=null){
                loadingDialog.dismiss()
                branchInfo = response
                tvBranch.text = "${branchInfo.branchId} ${branchInfo.branchName}"
                warehousesAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, branchInfo.warehouses)
                spWarehouses.adapter = warehousesAdapter
                documentData.branchId = branchInfo.branchId
                documentData.warehouseId = response.warehouses[0].warehouseId
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
                if(s.toString().isNotEmpty() && etFolio.text.isNotEmpty()) {
                    processProductRead(tools.cleanScannerReaderV1(s.toString()))
                    etScanBox.text = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        etFolio.setOnKeyListener{ _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                loadingDialog.show()
                processFolio(etFolio.text.toString())
                true
            }
            false
        }
    }

    private fun processProductRead(result: String){
        if(result.isNotEmpty()){
            var data = result.split("|")
            if(data.size>1){
                viewModel.loadItemDetails(data[0].trim().toInt(), data[1].trim().toInt(), data[2].trim().toInt(), data[3].trim().toFloat())
                viewModel.getItemDetails().observe(this, Observer{ response ->
                    loadingDialog.dismiss()
                    tools.hideKeyBoard(this)
                    if(response!=null && response.qrId>0) viewModel.addCapturedItem(response)
                    else tools.showMessageDialog(this, "Error", "Producto No Encontrado!")
                })
            }
        }
    }

    private fun processFolio(result: String){
        if(result.isNotEmpty()){
            etFolio.error = null
            Log.e("RES", "${result.toLong()}")
            viewModel.validateFolio(result.toLong(), (spWarehouses.selectedItem as WarehouseConfig).warehouseId)
            viewModel.getValidFolio().observe(this, Observer { response ->
                loadingDialog.dismiss()
                etScanBox.isEnabled = (response == null || response.code<1)
                if(response == null || response.code>0) {
                    etFolio.setText("")
                    tools.showMessageDialog(this, "Error","Folio de salida NO valido!")
                }else {
                    etFolio.isEnabled = false
                    tools.showMessageDialog(
                        this,
                        "Folio Correcto!",
                        "Puedes continuar con la captura"
                    )
                }
            })
        }else etFolio.error = "Campo Requerido!"
    }

    private fun saveDocument(){
        documentData.departureFolio = etFolio.text.toString().toLong()
        documentData.productsList.addAll(Realm.getDefaultInstance().copyFromRealm(captureAdapter.items!!))
        Log.e("SAVE", "" +  Gson().toJson(documentData))
        viewModel.saveToServer(documentData)
        viewModel.getSaveServerResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response!=null && response.code == 0){
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
            if(response!=null && response.code == 0){
                viewModel.deleteCapture()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    private fun showDetailsDialog(item: WarehouseTransferItem){
        tools.getWarehouseTransferItemDetails(item, this).show()
    }

    private fun confirmItemDelete(item: WarehouseTransferItem){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_item_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, _ ->
            viewModel.deleteItem(item)
            dialog.dismiss()
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, _ ->
            captureAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showDocumentDeleteConfirm(){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_message))
        builder.setNegativeButton(R.string.btn_accept) { _, _ ->
            loadingDialog.show()
            viewModel.deleteInServer(documentData.captureFolio, documentData.warehouseId)
            viewModel.getDeleteServerResponse().observe(this, Observer { response ->
                loadingDialog.dismiss()
                if(response!=null && response.code == 0) tools.showEndMessage(response.message, this)
                else tools.showMessageDialog(this, "Atencion", "Error al Cancelar Documento!")
            })
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

}