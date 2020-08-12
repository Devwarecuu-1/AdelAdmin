package com.adelnor.adeladmin.ui.activities.editors.property.departures.drts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.adelnor.adeladmin.ui.adapters.CaptureAdapter
import com.adelnor.adeladmin.utils.CommonTools
import com.adelnor.adeladmin.utils.ItemSelectedListener
import com.google.gson.Gson
import io.realm.Realm
import kotlin.collections.ArrayList

class DepartureByReturnToSupplierActivity : AppCompatActivity() {

    private var viewModel = DepartureByReturnToSupplierViewModel()
    private lateinit var sToolBar: Toolbar
    private lateinit var rvItems: RecyclerView
    private lateinit var spWarehouses: Spinner
    private lateinit var spProvider: Spinner
    private lateinit var btSave: Button
    private lateinit var btDelete: Button
    private lateinit var tvBranch: TextView
    private lateinit var etScanBox: EditText
    private lateinit var etFolio: EditText
    private lateinit var tvTitle: TextView
    private lateinit var branchInfo: BranchInfo
    private lateinit var captureAdapter: CaptureAdapter
    private lateinit var warehousesAdapter: ArrayAdapter<WarehouseConfig>
    private lateinit var providersAdapter: ArrayAdapter<Supplier>
    private lateinit var documentData: SupplierReturnDocument
    private var tools = CommonTools()
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departure_by_return_to_provider)
        sToolBar = findViewById(R.id.toolbar)
        rvItems = findViewById(R.id.recycler_capture)
        spWarehouses = findViewById(R.id.spinner_warehouses)
        spProvider = findViewById(R.id.spinner_providers)
        btSave = findViewById(R.id.button_capture_save)
        btDelete = findViewById(R.id.button_capture_delete)
        tvBranch = findViewById(R.id.text_branch)
        etScanBox = findViewById(R.id.edit_scan_box)
        etFolio = findViewById(R.id.edit_scan_purchase_order)
        tvTitle = findViewById(R.id.text_title)
        setSupportActionBar(sToolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        tvTitle.text = intent.getStringExtra("menu-item")
        loadingDialog = tools.getLoadingDialog(this)
        etFolio.visibility = View.GONE
        setupView()

        btSave.setOnClickListener{
            loadingDialog.show()
            if(captureAdapter.items!!.isNotEmpty()){
                loadingDialog.show()
                if(documentData.supplierIDQR > 0) updateDocument()
                else saveDocument()
            }

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
        viewModel.deleteDocument()
        super.onBackPressed()
    }

    var itemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            showDetailsDialog(captureAdapter.items!![index]!!)
        }
    }

    private val itemTouchHelper: ItemTouchHelper =  ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            confirmItemDelete(captureAdapter.items!![viewHolder.adapterPosition]!!)
        }
    })

    fun loadSavedDocument(v: View){
        val inputDialog = tools.showFolioInput(this)
        inputDialog.show()
        val etFolio = inputDialog.findViewById<EditText>(R.id.edit_folio)
        val btLoad = inputDialog.findViewById<Button>(R.id.button_load)
        val warehouseId = warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId
        btLoad!!.setOnClickListener{
            if(etFolio!!.text.isNotEmpty()){
                loadingDialog.show()
                tools.hideKeyBoard(this)
                etFolio.error = null
                viewModel.loadSavedDocument(etFolio.text.toString().toInt(), warehouseId)
                viewModel.getSavedDocument().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    if(response!=null && response.purchaseOrder>0){
                        v.visibility = View.INVISIBLE
                        btDelete.visibility = View.VISIBLE
                        viewModel.addAllCapturedItems(response.productsList)
                        documentData = response
                        documentData.productsList = ArrayList()

                        for (i in 0 until providersAdapter.count) {
                            if (providersAdapter.getItem(i)!!.supplierId == response.supplierId) {
                                spProvider.setSelection(i)
                                spProvider.isEnabled = false
                                break
                            }
                        }

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

    private fun setupView(){
        loadingDialog.show()
        viewModel.deleteDocument()
        btDelete.visibility = View.INVISIBLE
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(rvItems)
        captureAdapter = CaptureAdapter(itemListener)
        rvItems.adapter = captureAdapter

        documentData = SupplierReturnDocument()
        documentData.companyId = viewModel.getCompany()
        documentData.supplierIDQR = 0
        documentData.purchaseOrder = 0

        viewModel.loadBranchConfig()
        viewModel.loadSuppliers()
        viewModel.getSuppliersList().observe(this, Observer { response ->
            providersAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, response)
            spProvider.adapter = providersAdapter
            documentData.supplierId = response[0].supplierId
        })

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

        spProvider.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                documentData.supplierId = (spProvider.selectedItem as Supplier).supplierId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spWarehouses.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                documentData.warehouseId = (spWarehouses.selectedItem as WarehouseConfig).warehouseId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etScanBox.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()) {
                    Log.e("QR", ""+s.toString())
                    processProductRead(tools.cleanScannerReaderV2(s.toString()))
                    etScanBox.text = null
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

    }

    private fun processProductRead(result: String){
        if(result.isNotEmpty()){
            val data = result.split("|")
            if(data.size>1){
                viewModel.loadItemDetails(data[0].trim().toInt(), data[1].trim().toInt(), data[2].trim().toInt(), data[3].trim().toFloat())
                viewModel.getItemDetails().observe(this, Observer{ response ->
                    tools.hideKeyBoard(this)
                    if(response!=null && response.productId>0) viewModel.addCapturedItem(response)
                    else tools.showMessageDialog(this, "Error", "Producto No Encontrado!")
                })
            }
        }
    }

    private fun saveDocument(){
        documentData.productsList.addAll(Realm.getDefaultInstance().copyFromRealm(captureAdapter.items!!))
        Log.e("SAVE", "" +  Gson().toJson(documentData))
        viewModel.saveToServer(documentData)
        viewModel.getSaveServerResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if(response.code == 0){
                viewModel.deleteDocument()
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
                viewModel.deleteDocument()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    private fun showDetailsDialog(item: ItemExtended){
        tools.getEntryProductDetailsDialog(item,this).show()
    }

    private fun confirmItemDelete(item: ItemExtended){
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
        builder.setNegativeButton(R.string.btn_accept) { dialog, _ ->
            loadingDialog.show()
            viewModel.deleteInServer(documentData.supplierIDQR, documentData.warehouseId)
            viewModel.getDeleteServerResponse().observe(this, Observer { response ->
                dialog.dismiss()
                if(response!=null && response.code == 0) tools.showEndMessage(response.message, this)
                else tools.showMessageDialog(this, "Atencion", "Error al Cancelar Documento!")
            })
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

}