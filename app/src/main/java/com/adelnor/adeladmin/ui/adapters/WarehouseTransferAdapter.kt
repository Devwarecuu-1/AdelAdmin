package com.adelnor.adeladmin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.db.OutItemExtended
import com.adelnor.adeladmin.model.db.WarehouseTransferItem
import com.adelnor.adeladmin.utils.ItemSelectedListener
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_current_outlet.view.*

class WarehouseTransferAdapter(private var listener: ItemSelectedListener)
    : RecyclerView.Adapter<WarehouseTransferAdapter.CaptureViewHolder>() {

    var items: RealmResults<WarehouseTransferItem>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CaptureViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_current_outlet, parent, false)
    )

    override fun onBindViewHolder(holder: CaptureViewHolder, position: Int) {
        holder.bind(items?.get(position)!!, listener)
    }

    class CaptureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val llBackground = itemView.layout_background
        val tvQR = itemView.text_qr
        val tvAddress = itemView.text_address
        val tvCode = itemView.text_code
        val tvDescripcion = itemView.text_description
        var listener: ItemSelectedListener? = null

        fun bind(item: WarehouseTransferItem, listener: ItemSelectedListener) {
            tvQR.text = "${item.qrId}"
            tvAddress.text = "NA"
            tvCode.text = "${item.intProductId}"
            tvDescripcion.text = item.description
            this.listener = listener
            itemView.setOnClickListener(this)

            /*if(item.read)
                llBackground.setBackgroundColor(itemView.context.resources.getColor(R.color.appBlue1))*/

        }

        override fun onClick(v: View?) {
            this.listener?.onItemSelected(v!!, adapterPosition)
        }
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items!!.size
}