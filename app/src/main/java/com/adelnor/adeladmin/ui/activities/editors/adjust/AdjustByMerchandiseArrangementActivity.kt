package com.adelnor.adeladmin.ui.activities.editors.adjust

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
import com.adelnor.adeladmin.model.api.MerchandiseArraDocument
import com.adelnor.adeladmin.model.api.WarehouseConfig
import com.adelnor.adeladmin.model.db.MerchandiseArraItem
import com.adelnor.adeladmin.ui.adapters.CapureMerchandise
import com.adelnor.adeladmin.ui.adapters.CapureTransferOut
import com.adelnor.adeladmin.utils.CommonTools
import com.adelnor.adeladmin.utils.ItemSelectedListener
import com.google.gson.Gson
import kotlin.random.Random

class AdjustByMerchandiseArrangementActivity : AppCompatActivity(){
    private lateinit var viewModel: AdjustByMerchandiseArrangementViewModel
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
    private lateinit var documentAdapter: CapureMerchandise
    private lateinit var adapter: CapureMerchandise
    private lateinit var documentData: MerchandiseArraDocument
    private lateinit var branchInfo: BranchInfo
    val list = ArrayList<MerchandiseArraItem>()

    private var tools = CommonTools()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AdjustByMerchandiseArrangementViewModel()
        setContentView(R.layout.activity_merchandise_arrangement)
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
        etReadFolio.hint = "Domicilio"
        etReadFolio.isEnabled = true
        etScanBox.isEnabled = false
        tvTitle.text = intent.getStringExtra("menu-item")




        adapter = CapureMerchandise(list)
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
                saveDocument()
            }
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
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.show()
    }

    private fun setupView() {
        loadingDialog.show()
        viewModel.deleteDocument()
        btDelete.visibility = View.INVISIBLE


        documentData = MerchandiseArraDocument()
        documentData.user = viewModel.getUser()
        documentData.macAddress = tools.getMacAddress(this)
        documentData.productsList = ArrayList()

        viewModel.loadBranchConfig()
        viewModel.getBranchConfig().observe(this, Observer { response ->
            if (response != null) {
                loadingDialog.dismiss()
                branchInfo = response
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

        etReadFolio.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){

                    processDomiRead(tools.cleanScannerReaderV1(s.toString()))


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


    fun showDetailsDialog(item: MerchandiseArraItem) {
        tools.getMerchandise(item, this).show()
        adapter.notifyDataSetChanged()
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
                //Si el campo es mayor que 1 el campo a guardar sera el Numero de Folio ingresado
                viewModel.loadProductDetails(
                    data[0].trim().toInt(),
                    data[1].trim().toInt(),
                    data[2].trim().toInt(),
                    data[3].trim().toFloat())
                //Una ves quede guardado haremos un llamado a un JSON del producto = response
                viewModel.getProductDetails().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    tools.hideKeyBoard(this)
                    if(response!=null){

                        response.read = true
                        val index = Random.nextInt(8)
                        val newItem = MerchandiseArraItem(response.niDQR,response.intProductCod,response.description,response.presentationId,response.description,response.type,response.quantity,response.lot,response.expirationDate,response.elaborationDate,response.read)
                        list += newItem

                        adapter.notifyItemInserted(index)
                    }else tools.showMessageDialog(this, "Error", "Producto No Encontrado!")
                })
            }
        }

    }
    private fun processDomiRead(result: String){
        Log.e("READ_QR", ""+ result)
        // Si el Qr no esta vacio Y NO EXCEDE EL TAMAÑO DE 8 CARACTERES
        if(result.isNotEmpty() && result.length == 8){

                //Extraemos el detalle del producto del path
                viewModel.loadDomi(warehousesAdapter.getItem(spWarehouses.selectedItemPosition)!!.warehouseId, result)

                //Una ves quede guardado haremos un llamado a un JSON del producto = response
                viewModel.getDomiDetails().observe(this, Observer { response ->
                    loadingDialog.dismiss()
                    tools.hideKeyBoard(this)
                    //Si el resultado no esta vacio y el code es igual a 0
                    if(response!=null && response.code == 0){
                        documentData.domiId  = result
                        etScanBox.isEnabled = true
                        etScanBox.requestFocus()
                        etReadFolio.hint = response.message
                        etReadFolio.text = null
                        etReadFolio.isEnabled = false
                    }else {
                        tools.showMessageDialog(this, "Error", "NO EXISTE ESA UBICACIÓN EN EL ALMACÉN")
                        etReadFolio.text = null
                        etReadFolio.isSelected
                    }
                })
        }else {
            //Si no, mostrar un error de codigo y borrar lo del EditText
            tools.showMessageDialog(this, "Error", "EL DOMICILIO NO ES UN CODIGO VALIDO")
            etReadFolio.text = null
            etReadFolio.isSelected
        }

    }

}