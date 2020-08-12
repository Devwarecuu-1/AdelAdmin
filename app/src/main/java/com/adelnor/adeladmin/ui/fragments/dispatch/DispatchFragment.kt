package com.adelnor.adeladmin.ui.fragments.dispatch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.MenuItem
import com.adelnor.adeladmin.ui.activities.editors.consignment.departures.dcts.DepartureByRConsignmentToSuppActivity
import com.adelnor.adeladmin.ui.activities.editors.consignment.entries.dow.DepartureByTransferToWarehouseActivity
import com.adelnor.adeladmin.ui.activities.editors.consignment.entries.ebsc.EntryBySupplierConsignmentActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.epr.EntryByPurchaseReturnActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.esp.EntryBySupplierPurchaseActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.et.EntryByTransferActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.iae.EntryByInventoryAdjustmentActivity
import com.adelnor.adeladmin.ui.adapters.MenuAdapter
import com.adelnor.adeladmin.utils.ItemSelectedListener

class DispatchFragment : Fragment() {

    private lateinit var dispatchViewModel: DispatchViewModel
    private lateinit var rvEntryMenu: RecyclerView
    private lateinit var rvDepartureMenu: RecyclerView
    private var entriesMenu: ArrayList<MenuItem> = ArrayList()
    private var departuresMenu: ArrayList<MenuItem> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        dispatchViewModel =
                ViewModelProviders.of(this).get(DispatchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dispatch, container, false)
        rvEntryMenu = root.findViewById(R.id.recycler_disp_entry)
        rvDepartureMenu = root.findViewById(R.id.recycler_disp_outlet)
        rvEntryMenu.layoutManager = LinearLayoutManager(context)
        rvDepartureMenu.layoutManager = LinearLayoutManager(context)
        setupView()
        return root
    }

    var entriesItemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            var intent = getEntryScreen(index)
            intent.putExtra("menu-item", entriesMenu.get(index).title)
            startActivity(intent)
        }
    }

    var departuresItemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            var intent = getDepartureScreen(index)
            intent.putExtra("menu-item", departuresMenu.get(index).title)
            startActivity(intent)
        }
    }

    private fun setupView(){
        entriesMenu.add(MenuItem("Entrada por Consignacion de Proveedor", R.drawable.ic_mi_co_in_02))
        entriesMenu.add(MenuItem("Salida por transferencia a Almacen propio", R.drawable.ic_mi_co_in_03))

        departuresMenu.add(MenuItem("Salida por Devolucion por Consignacion a proveedor", R.drawable.ic_mi_co_ou_01))

        rvEntryMenu.adapter = MenuAdapter(entriesMenu, requireContext(), entriesItemListener)
        rvDepartureMenu.adapter = MenuAdapter(departuresMenu, requireContext(), departuresItemListener)
    }

    private fun getEntryScreen(index: Int): Intent {
        var intent =  when(index){
            0 -> Intent(activity, EntryBySupplierConsignmentActivity::class.java)
            1 -> Intent(activity, DepartureByTransferToWarehouseActivity::class.java)
            else -> null
        }
        return intent!!
    }

    private fun getDepartureScreen(index: Int): Intent {
        var intent =  when(index){
            0 -> Intent(activity, DepartureByRConsignmentToSuppActivity::class.java)
            else -> null
        }
        return intent!!
    }
}