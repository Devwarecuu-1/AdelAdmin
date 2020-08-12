package com.adelnor.adeladmin.ui.activities.editors.property.departures.drs

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
import com.adelnor.adeladmin.model.db.SupplierReTagItem
import com.adelnor.adeladmin.ui.adapters.CaptureAdapter
import com.adelnor.adeladmin.ui.adapters.OutletCaptureAdapter
import com.adelnor.adeladmin.ui.adapters.SupplierReTagAdapter
import com.adelnor.adeladmin.utils.CommonTools
import com.adelnor.adeladmin.utils.ItemSelectedListener
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.Realm.getDefaultInstance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class DepartureByRTaggingBySupplierActivity : AppCompatActivity() {

    private lateinit var viewModel: DepartureByRTaggingBySupplierViewModel
    private lateinit var sToolBar: Toolbar
    private lateinit var rvItems: RecyclerView
    private lateinit var spWarehouses: Spinner
    private lateinit var spProvider: Spinner
    private lateinit var btSave: Button
    private lateinit var btDelete: Button
    private lateinit var tvTitle: TextView
    private lateinit var tvSupplierLabel: TextView
    private lateinit var tvBranch: TextView
    private lateinit var etScanBox: EditText
    private lateinit var etReadFolio: EditText
    private lateinit var loadingDialog: AlertDialog
    private lateinit var warehousesAdapter: ArrayAdapter<WarehouseConfig>
    private lateinit var documentAdapter: SupplierReTagAdapter
    private lateinit var documentData: SupplierReTagDocument
    private lateinit var branchInfo: BranchInfo
    private var tools = CommonTools()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = DepartureByRTaggingBySupplierViewModel()
        setContentView(R.layout.activity_departure_by_r_tagging_by_supplier)
        sToolBar = findViewById(R.id.toolbar)
        rvItems = findViewById(R.id.recycler_capture)
        spWarehouses = findViewById(R.id.spinner_warehouses)
        btSave = findViewById(R.id.button_capture_save)
        btDelete = findViewById(R.id.button_capture_delete)
        tvBranch = findViewById(R.id.text_branch)
        tvTitle = findViewById(R.id.text_title)
        tvSupplierLabel = findViewById(R.id.text_label_supplier)
        etScanBox = findViewById(R.id.edit_scan_box)
        loadingDialog = tools.getLoadingDialog(this)
        spProvider = findViewById(R.id.spinner_providers)
        etReadFolio = findViewById(R.id.edit_scan_purchase_order)
        spProvider.visibility = GONE
        tvSupplierLabel.visibility = GONE
        etReadFolio.hint = "Proveedor"
        etReadFolio.isEnabled = false
        setSupportActionBar(sToolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        tvTitle.text = intent.getStringExtra("menu-item")
        setupView()

        btSave.setOnClickListener {
            if(documentAdapter.items!!.isNotEmpty()){
                loadingDialog.show()
                documentData.productsList.addAll(getDefaultInstance().copyFromRealm(documentAdapter.items!!))
                if(documentData.documentFolio>0) updateDocument()
                else saveDocument()
            }else tools.showMessageDialog(this, "Atencion!", "El documento esta vacio!")
        }

        btDelete.setOnClickListener { showDocumentDeleteConfirm() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        viewModel.deleteDocument()
        super.onBackPressed()
    }

    private var itemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            showDetailsDialog(documentAdapter.items!![index]!!)
        }
    }

    private val itemTouchHelper: ItemTouchHelper =  ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            confirmItemDelete(documentAdapter.items!![viewHolder.adapterPosition]!!)
        }
    })

    fun loadSavedDocument(v: View){
        var inputDialog = tools.showFolioInput(this)
        inputDialog.show()
        val etFolio = inputDialog.findViewById<EditText>(R.id.edit_folio)
        val btLoad = inputDialog.findViewById<Button>(R.id.button_load)
        btLoad!!.setOnClickListener{
            etFolio!!.error = if(etFolio.text.isNullOrEmpty()) "Campo Requerido!" else null

            if(etFolio!!.text.isNotEmpty()){
                loadingDialog.show()
                tools.hideKeyBoard(this)
                etFolio.error = null
                viewModel.loadSavedDocument(etFolio.text.toString().toInt(),
                    warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId)
                viewModel.getSavedDocument().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    if(response!=null && response.documentFolio>0){
                        v.visibility = View.INVISIBLE
                        btDelete.visibility = View.VISIBLE
                        documentData = response
                        viewModel.saveDocumentItems(response.productsList)
                        documentData.productsList = ArrayList()

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
            }
        }
    }

    private fun setupView(){
        loadingDialog.show()
        viewModel.deleteDocument()
        btDelete.visibility = View.INVISIBLE
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(rvItems)
        documentAdapter = SupplierReTagAdapter(itemListener)
        rvItems.adapter = documentAdapter

        documentData = SupplierReTagDocument()
        documentData.user = viewModel.getUser()
        documentData.macAddress = tools.getMacAddress(this)
        documentData.productsList = ArrayList()

        viewModel.loadBranchConfig()
        viewModel.getBranchConfig().observe(this, Observer { response ->
            if(response!=null){
                loadingDialog.dismiss()
                branchInfo = response
                documentData.branchId = branchInfo.branchId
                tvBranch.text = "${response.branchName}"
                warehousesAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, branchInfo.warehouses)
                spWarehouses.adapter = warehousesAdapter
            }else tools.showMessageDialog(this, "Error", "Error cargando datos!")
        })

        viewModel.subscribeToItems().observe(this, Observer{ response ->
            documentAdapter.items = response
            documentAdapter.notifyDataSetChanged()
        })

        spWarehouses.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                documentData.warehouseId = (spWarehouses.selectedItem as WarehouseConfig).warehouseId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etScanBox.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                    processProductRead(tools.cleanScannerReaderV1(s.toString()))
                    etScanBox.text = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun processProductRead(result: String){
        Log.e("READ_QR", ""+ result)
        if(result.isNotEmpty()){
            var data = result.split("|")
            if(data.size>1){
                viewModel.loadProductDetails(data[0].trim().toInt(), data[1].trim().toInt(), data[2].trim().toInt(), data[3].trim().toInt())
                viewModel.getProductDetails().observe(this, Observer { response ->
                    if(response!=null && response.qrId>0){
                        this.etReadFolio.setText(" ${response.supplierId} - ${response.supplierName} ")
                        documentData.supplierId = response.supplierId
                        viewModel.saveDocumentItem(response)
                    }else tools.showMessageDialog(this, "Error", "Producto No Encontrado!")
                })
            }
        }
    }

    private fun saveDocument(){
        Log.e("SAVE", "" +  Gson().toJson(documentData))
        viewModel.saveDocument(documentData)
        viewModel.getSaveResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response.code == 0){
                viewModel.deleteDocument()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    private fun updateDocument(){
        Log.e("UPDATE", "" +  Gson().toJson(documentData))
        viewModel.updateDocument(documentData)
        viewModel.getUpdateResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response.code == 0){
                viewModel.deleteDocument()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    private fun showDetailsDialog(item: SupplierReTagItem){
        tools.getSupplierReTagItemDetails(item,this).show()
    }

    private fun confirmItemDelete(item: SupplierReTagItem){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_item_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, which ->
            viewModel.deleteDocumentItem(item)
            dialog.dismiss()
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, which ->
            documentAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showDocumentDeleteConfirm(){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, which ->
            loadingDialog.show()
            viewModel.deleteServerDocument(documentData.documentFolio, documentData.warehouseId)
            viewModel.getDeleteResponse().observe(this, Observer { response ->
                dialog.dismiss()
                if(response!=null && response.code == 0){
                    tools.showEndMessage(response.message, this)
                    this.finish()
                } else tools.showMessageDialog(this, "Atencion", "Error al Cancelar Documento!")
            })
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

}