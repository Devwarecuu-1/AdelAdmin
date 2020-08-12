package com.adelnor.adeladmin.ui.activities.editors.consignment.entries.dow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.api.BranchInfo
import com.adelnor.adeladmin.model.api.DepartureTransferDocument
import com.adelnor.adeladmin.model.api.WarehouseConfig
import com.adelnor.adeladmin.model.db.DeapTransferItemPost
import com.adelnor.adeladmin.ui.adapters.CapureTransferOut
import com.adelnor.adeladmin.utils.CommonTools
import com.adelnor.adeladmin.utils.ItemSelectedListener
import com.google.gson.Gson
import kotlin.random.Random

class DepartureByTransferToWarehouseActivity : AppCompatActivity() {
    private lateinit var viewModel: DepartureByTransferToWarehouseViewModel
    private lateinit var sToolBar: Toolbar
    private lateinit var rvItems: RecyclerView
    private lateinit var spWarehouses: Spinner
    private lateinit var spProvider: Spinner
    private lateinit var btSave: Button
    private lateinit var btDelete: Button
    private lateinit var tvTitle: TextView
    private lateinit var tvBranch: TextView
    private lateinit var etScanBox: EditText
    private lateinit var etReadFolio: EditText
    private lateinit var loadingDialog: AlertDialog
    private lateinit var warehousesAdapter: ArrayAdapter<WarehouseConfig>
    private lateinit var documentAdapter: CapureTransferOut
    private lateinit var adapter: CapureTransferOut
    private lateinit var documentData: DepartureTransferDocument
    private lateinit var branchInfo: BranchInfo
    val list = ArrayList<DeapTransferItemPost>()

    private var tools = CommonTools()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = DepartureByTransferToWarehouseViewModel()
        setContentView(R.layout.activity_departure_by_transfer_to_warehouse)
        sToolBar = findViewById(R.id.toolbar)
        rvItems = findViewById(R.id.recycler_capture)
        spWarehouses = findViewById(R.id.spinner_warehouses)
        btSave = findViewById(R.id.button_capture_save)
        btDelete = findViewById(R.id.button_capture_delete)
        tvBranch = findViewById(R.id.text_branch)
        tvTitle = findViewById(R.id.text_title)
        etScanBox = findViewById(R.id.edit_scan_box)
        loadingDialog = tools.getLoadingDialog(this)
        spProvider = findViewById(R.id.spinner_providers)
        etReadFolio = findViewById(R.id.edit_scan_purchase_order)
        spProvider.visibility = View.GONE
        etReadFolio.hint = "Folio Entrega"
        etReadFolio.isEnabled = true
        tvTitle.text = intent.getStringExtra("menu-item")




        adapter = CapureTransferOut(list)
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(rvItems)
        itemTouchHelperClick.attachToRecyclerView(rvItems)


        rvItems.adapter = adapter
        setupView()

        btSave.setOnClickListener {
            if (list.isEmpty()) {
                tools.showMessageDialog(this, "Atencion!", "El documento esta vacio!")
            } else {
                loadingDialog.show()
                documentData.productsList.addAll(list)
                if (documentData.captureFolio > 0) updateDocument()
                else saveDocument()
            }
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

    var itemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            showDetailsDialog(list[index])
        }
    }

    val itemTouchHelper: ItemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                confirmItemDelete(viewHolder.adapterPosition)

            }


        })

    val itemTouchHelperClick: ItemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDetailsDialog(list[viewHolder.adapterPosition])
            }


        })


    private fun confirmItemDelete(item: Int){
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_item_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, which ->

            list.removeAt(item)
            documentData.productsList.drop(item)


            dialog.dismiss()
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, which ->
            documentAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.show()
    }
    fun loadSavedDocument(v: View) {
        var inputDialog = tools.showFolioInput(this)
        inputDialog.show()
        val etFolio = inputDialog.findViewById<EditText>(R.id.edit_folio)
        val btLoad = inputDialog.findViewById<Button>(R.id.button_load)
        btLoad!!.setOnClickListener {
            etFolio!!.error = if (etFolio.text.isNullOrEmpty()) "Campo Requerido!" else null

            if (etFolio!!.text.isNotEmpty()) {
                loadingDialog.show()
                tools.hideKeyBoard(this)
                viewModel.loadSavedDocument(
                    etFolio.text.toString().toInt(),
                    warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId
                )
                Log.e("Test", etFolio.text.toString() + warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId)
                viewModel.getSavedDocument().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    if (response != null) {
                        v.visibility = View.INVISIBLE
                        btDelete.visibility = View.VISIBLE

                        documentData = response
                        list.addAll(documentData.productsList)
                        documentData.user = viewModel.getUser()
                        documentData.macAddress = tools.getMacAddress(this)
                        viewModel.saveDocumentItems(response.productsList)
                        documentData.productsList = ArrayList()

                        for (i in 0 until warehousesAdapter.count) {
                            if (warehousesAdapter.getItem(i)!!.warehouseId == response.codAlmInterno) {
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

    private fun setupView() {
        loadingDialog.show()
        viewModel.deleteDocument()
        btDelete.visibility = View.INVISIBLE


        documentData = DepartureTransferDocument()
        documentData.user = viewModel.getUser()
        documentData.macAddress = tools.getMacAddress(this)
        documentData.productsList = ArrayList()

        viewModel.loadBranchConfig()
        viewModel.getBranchConfig().observe(this, Observer { response ->
            if (response != null) {
                loadingDialog.dismiss()
                branchInfo = response
                documentData.branchId = branchInfo.branchId
                tvBranch.text = "${response.branchName}"
                warehousesAdapter =
                    ArrayAdapter(applicationContext, R.layout.spinner_item, branchInfo.warehouses)
                spWarehouses.adapter = warehousesAdapter
            } else tools.showMessageDialog(this, "Error", "Error cargando datos!")
        })


        spWarehouses.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                documentData.codAlmInterno =
                    (spWarehouses.selectedItem as WarehouseConfig).warehouseId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etScanBox.addTextChangedListener(object: TextWatcher {
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



    fun saveDocument() {
        Log.e("SAVE", "" + Gson().toJson(documentData))
        viewModel.saveDocument(documentData)
        viewModel.getSaveResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if (response.code == 0) {
                viewModel.deleteDocument()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    fun updateDocument() {
        Log.e("UPDATE", "" + Gson().toJson(documentData))
        viewModel.updateDocument(documentData)
        viewModel.getUpdateResponse().observe(this, Observer { response ->
            loadingDialog.dismiss()
            if (response.code == 0) {
                viewModel.deleteDocument()
                tools.showDocumentSaveConfirmation(response.message, this)
            } else tools.showCaptureError(this)
        })
    }

    fun showDetailsDialog(item: DeapTransferItemPost) {
        tools.getDeapTransferItem(item, this).show()
        adapter.notifyDataSetChanged()
    }




    private fun showDocumentDeleteConfirm() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_capture_delete_message))
        builder.setNegativeButton(R.string.btn_accept) { dialog, which ->
            loadingDialog.show()
            Log.e("test", documentData.captureFolio.toString() + documentData.codAlmInterno.toString() + documentData.user.toString() + documentData.macAddress.toString())
            viewModel.deleteServerDocument(documentData.captureFolio, documentData.codAlmInterno, documentData.user, documentData.macAddress)

            viewModel.getDeleteResponse().observe(this, Observer { response ->
                dialog.dismiss()
                if (response != null && response.code == 0) tools.showEndMessage(
                    response.message,
                    this
                )
                else tools.showMessageDialog(this, "Atencion", "Error al Cancelar Documento!")
            })
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    // Funcion para guardar valores en el grid
    private fun processProductRead(result: String){
        Log.e("READ_QR", ""+ result)
        // Si el Qr no esta vacio
        if(result.isNotEmpty()){
            //Dividimos el contenido del QR con un "|" para poder utilizarlo y lo guardamos en data
            var data = result.split("|")
            //si data no es un solo dato
            if(data.size>1){
                //si el folio de lectura es null agregamos un 0 en el campo a guardar en loadProductDetail
                if(etReadFolio.getText().toString() == "") {
                    viewModel.loadProductDetails(
                        data[0].trim().toInt(),
                        data[1].trim().toInt(),
                        data[2].trim().toInt(),
                        data[3].trim().toFloat(),
                        0
                    )
                }
                //Si el campo es mayor que 1 el campo a guardar sera el Numero de Folio ingresado
                else viewModel.loadProductDetails(
                    data[0].trim().toInt(),
                    data[1].trim().toInt(),
                    data[2].trim().toInt(),
                    data[3].trim().toFloat(),
                    Integer.parseInt(etReadFolio.getText().toString())
                )
                //Una ves quede guardado haremos un llamado a un JSON del producto = response
                viewModel.getProductDetails().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    tools.hideKeyBoard(this)
                    if(response!=null && response.active == 0 && response.read == false){
                        this.etReadFolio.setText("${response.outFol}")

                        response.read = true
                        val index = Random.nextInt(8)
                        val newItem = DeapTransferItemPost(response.cantDep,response.nMax,response.niDQR,response.intProductCod,response.codProd,response.description,response.presentationId,response.quantity,response.type,response.lot,response.expirationDate,response.elaborationDate,response.descriptionPresent,response.factorEnvase,response.factorEmpaque)
                        list += newItem
                        adapter.notifyItemInserted(index)
                    }else tools.showMessageDialog(this, "Error", "Producto No Encontrado!")
                })
            }
        }

    }
}