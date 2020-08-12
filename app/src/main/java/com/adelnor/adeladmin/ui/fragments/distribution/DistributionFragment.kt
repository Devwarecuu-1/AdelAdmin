package com.adelnor.adeladmin.ui.fragments.distribution

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.MenuItem
import com.adelnor.adeladmin.ui.adapters.MenuAdapter
import com.adelnor.adeladmin.utils.ItemSelectedListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DistributionFragment : Fragment() {

    private lateinit var rvEntryMenu: RecyclerView
    private lateinit var rvOutletMenu: RecyclerView
    private var entriesMenu: ArrayList<MenuItem> = ArrayList()
    private var outletsMenu: ArrayList<MenuItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_distribution, container, false)
        rvEntryMenu = root.findViewById(R.id.recycler_dist_entry)
        rvOutletMenu = root.findViewById(R.id.recycler_dist_outlet)
        rvEntryMenu.layoutManager = LinearLayoutManager(context)
        rvOutletMenu.layoutManager = LinearLayoutManager(context)
        setupView()

        return root
    }

    var entriesItemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            Log.e("SELECTED", "OP: ${entriesMenu.get(index).title}")
        }
    }

    var outletsItemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            Log.e("SELECTED", "OP: ${outletsMenu.get(index).title}")
        }
    }

    private fun setupView(){
        entriesMenu.add(MenuItem("Entrada por consignación a Distribuidor", R.drawable.ic_mi_di_in_01))

        outletsMenu.add(MenuItem("Salida por Devolucion a distribuidores", R.drawable.ic_mi_di_ou_01))

        rvEntryMenu.adapter = MenuAdapter(entriesMenu, requireContext(), entriesItemListener)
        rvOutletMenu.adapter = MenuAdapter(outletsMenu, requireContext(), outletsItemListener)
    }
}