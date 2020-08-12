package com.adelnor.adeladmin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.MenuItem
import com.adelnor.adeladmin.utils.ItemSelectedListener
import kotlinx.android.synthetic.main.item_options_menu.view.*

class MenuAdapter (
    private var items: ArrayList<MenuItem>,
    private var context: Context,
    private var itemListener: ItemSelectedListener)
    : RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MenuItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_options_menu, parent, false)
    )

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(items.get(position), itemListener, context)
    }

    class MenuItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private val tName = itemView.text_menu_title
        private val ivResource = itemView.image_menu_resource
        var listener: ItemSelectedListener? = null

        fun bind(item: MenuItem, itemListener: ItemSelectedListener, context: Context){
            tName.text = item.title
            ivResource.setImageDrawable(getDrawable(context, item.resource))
            this.listener = itemListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onItemSelected(v!!, adapterPosition)
        }
    }

    override fun getItemCount() = items.size

}