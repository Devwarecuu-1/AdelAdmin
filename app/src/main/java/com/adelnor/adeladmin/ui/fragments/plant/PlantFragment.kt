package com.adelnor.adeladmin.ui.fragments.plant

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
import com.adelnor.adeladmin.ui.adapters.MenuAdapter
import com.adelnor.adeladmin.utils.ItemSelectedListener

class PlantFragment : Fragment() {

    private lateinit var plantViewModel: PlantViewModel
    private lateinit var rvMenu: RecyclerView
    private var menu: ArrayList<MenuItem> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        plantViewModel =
                ViewModelProviders.of(this).get(PlantViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_plant, container, false)
        rvMenu = root.findViewById(R.id.recycler_plant_menu)
        rvMenu.layoutManager = LinearLayoutManager(context)
        setupView()

        return root
    }

    var menuItemListener: ItemSelectedListener = object : ItemSelectedListener {
        override fun onItemSelected(view: View, index: Int) {
            Log.e("SELECTED", "OP: ${menu.get(index).title}")
        }
    }

    private fun setupView(){
        menu.add(MenuItem("Orden de produccion(Mezcla de Fertilizantes)", R.drawable.ic_mi_pl_pr_01))
        menu.add(MenuItem("Orden de produccion(Planta)", R.drawable.ic_mi_pl_pr_02))
        rvMenu.adapter = MenuAdapter(menu, requireContext(), menuItemListener)
    }

}