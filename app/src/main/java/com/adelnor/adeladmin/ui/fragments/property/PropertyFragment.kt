package com.adelnor.adeladmin.ui.fragments.property

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.MenuItem
import com.adelnor.adeladmin.ui.activities.editors.adjust.AdjustByMerchandiseArrangementActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.dbh.DepartureByBagHandlingActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.esp.EntryBySupplierPurchaseActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.dcd.DepartureByCustomerDeliveryActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.del.DepartureByExpirationOrLossesActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.dia.DepartureByInventoryAdjustmentActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.dmp.DepartureByMaquilaPackagingActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.drts.DepartureByReturnToSupplierActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.drs.DepartureByRTaggingBySupplierActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.dt.DepartureByTransferActivity
import com.adelnor.adeladmin.ui.activities.editors.property.departures.dum.DepartureByUnbilledMerchandiseActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.epr.EntryByPurchaseReturnActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.et.EntryByTransferActivity
import com.adelnor.adeladmin.ui.activities.editors.property.entries.iae.EntryByInventoryAdjustmentActivity
import com.adelnor.adeladmin.ui.adapters.MenuAdapter
import com.adelnor.adeladmin.utils.ItemSelectedListener

class PropertyFragment : Fragment() {

    private lateinit var propertyViewModel: PropertyViewModel
    private lateinit var rvEntriesMenu: RecyclerView
    private lateinit var rvDeparturesMenu: RecyclerView
    private var entriesMenu: ArrayList<MenuItem> = ArrayList()
    private var departuresMenu: ArrayList<MenuItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        propertyViewModel =
            ViewModelProviders.of(this).get(PropertyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_property, container, false)
        rvEntriesMenu = root.findViewById(R.id.recycler_menu_entry)
        rvDeparturesMenu = root.findViewById(R.id.recycler_menu_outlet)
        rvEntriesMenu.layoutManager = LinearLayoutManager(context)
        rvDeparturesMenu.layoutManager = LinearLayoutManager(context)
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

    var departureItemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            var intent = getDepartureScreen(index)
            intent.putExtra("menu-item", departuresMenu.get(index).title)
            startActivity(intent)
        }
    }

    private fun setupView(){
        entriesMenu.add(MenuItem("Entrada por compra a proveedor", R.drawable.ic_mi_pd_in_01))
        entriesMenu.add(MenuItem("Entrada por Transferencia", R.drawable.ic_mi_pd_in_02))
        entriesMenu.add(MenuItem("Entrada por devolucion de venta a cliente", R.drawable.ic_mi_pd_in_03))
        entriesMenu.add(MenuItem("Entrada ajuste de inventario", R.drawable.ic_mi_pd_in_04))

        departuresMenu.add(MenuItem("Entrega Fisica a Clientes", R.drawable.ic_mi_pd_ou_01))
        departuresMenu.add(MenuItem("Salida de mercancia no facturada", R.drawable.ic_mi_pd_ou_02))
        departuresMenu.add(MenuItem("Salida por Transferencia", R.drawable.ic_mi_pd_ou_03))
        departuresMenu.add(MenuItem("Salida por Devolucion a proveedor", R.drawable.ic_mi_pd_ou_04))
        departuresMenu.add(MenuItem("Salida pór caducidad o mermas", R.drawable.ic_mi_pd_ou_05))
        departuresMenu.add(MenuItem("Salida por ajuste de inventario", R.drawable.ic_mi_pd_ou_06))
        departuresMenu.add(MenuItem("Salida por manejo de costales", R.drawable.ic_mi_pd_ou_07))
        departuresMenu.add(MenuItem("Salida por envasado de maquila", R.drawable.ic_mi_pd_ou_08))
        departuresMenu.add(MenuItem("Salida por reetiquetado por proveedor", R.drawable.ic_mi_pd_ou_09))
        departuresMenu.add(MenuItem("Acomodo de mercancia", R.drawable.ic_mi_pd_ou_09))

        rvEntriesMenu.adapter = MenuAdapter(entriesMenu, requireContext(), entriesItemListener)
        rvDeparturesMenu.adapter = MenuAdapter(departuresMenu, requireContext(), departureItemListener)
    }

    private fun getEntryScreen(index: Int): Intent{
        var intent =  when(index){
            0 -> Intent(activity, EntryBySupplierPurchaseActivity::class.java)
            1 -> Intent(activity, EntryByTransferActivity::class.java)
            2 -> Intent(activity, EntryByPurchaseReturnActivity::class.java)
            3 -> Intent(activity, EntryByInventoryAdjustmentActivity::class.java)
            else -> null
        }
        return intent!!
    }

    private fun getDepartureScreen(index: Int): Intent{
        var intent =  when(index){
            0 -> Intent(activity, DepartureByCustomerDeliveryActivity::class.java)
            1 -> Intent(activity, DepartureByUnbilledMerchandiseActivity::class.java)
            2 -> Intent(activity, DepartureByTransferActivity::class.java)
            3 -> Intent(activity, DepartureByReturnToSupplierActivity::class.java)
            4 -> Intent(activity, DepartureByExpirationOrLossesActivity::class.java)
            5 -> Intent(activity, DepartureByInventoryAdjustmentActivity::class.java)
            6 -> Intent(activity, DepartureByBagHandlingActivity::class.java)
            7 -> Intent(activity, DepartureByMaquilaPackagingActivity::class.java)
            8 -> Intent(activity, DepartureByRTaggingBySupplierActivity::class.java)
            9 -> Intent(activity, AdjustByMerchandiseArrangementActivity::class.java)
            else -> null
        }
        return intent!!
    }
}