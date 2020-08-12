package com.adelnor.adeladmin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.db.SupplierReTagItem
import com.adelnor.adeladmin.utils.ItemSelectedListener
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_current_capture.view.*

class SupplierReTagAdapter(private var listener: ItemSelectedListener)
    : RecyclerView.Adapter<SupplierReTagAdapter.SupplierReTagViewHolder>() {

    var items: RealmResults<SupplierReTagItem>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SupplierReTagViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_current_capture, parent, false)
    )

    override fun onBindViewHolder(holder: SupplierReTagViewHolder, position: Int) {
        holder.bind(items?.get(position)!!, listener)
    }

    class SupplierReTagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val tvCode = itemView.text_code
        val tvName = itemView.text_name
        val tvDescripcion = itemView.text_description
        val tvQuantity = itemView.text_quantity
        var listener: ItemSelectedListener? = null

        fun bind(item: SupplierReTagItem, listener: ItemSelectedListener){
            tvCode.text = "${item.productId}"
            tvName.text = item.description
            tvDescripcion.text = item.presentation
            tvQuantity.text = "${item.quantity}"
            this.listener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onItemSelected(v!!, adapterPosition)
        }

    }

    override fun getItemCount(): Int = if(items.isNullOrEmpty()) 0 else items!!.size

}