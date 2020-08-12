package com.adelnor.adeladmin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.db.DeapTransferItemPost
import com.adelnor.adeladmin.model.db.MerchandiseArraItem
import com.adelnor.adeladmin.ui.activities.editors.property.departures.dt.DepartureByTransferViewModel
import com.adelnor.adeladmin.utils.CommonTools
import kotlinx.android.synthetic.main.item_current_outlet.view.*
import kotlinx.android.synthetic.main.item_current_outlet.view.text_code
import kotlinx.android.synthetic.main.item_current_outlet.view.text_qr
import kotlinx.android.synthetic.main.item_current_transfer.view.*

class CapureMerchandise(
    private val exampleList: List<MerchandiseArraItem>
) :
    RecyclerView.Adapter<CapureMerchandise.ExampleViewHolder>() {
    private var mListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val x: DepartureByTransferViewModel
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_current_transfer,
            parent, false)
        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.textView0.text = currentItem.niDQR.toString()
        holder.textView1.text = currentItem.quantity.toString()
        holder.textView2.text = currentItem.description
        holder.textView3.text = currentItem.intProductCod
    }
    override fun getItemCount() = exampleList.size
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val textView0: TextView = itemView.text_qr
        val textView1: TextView = itemView.text_cantidades
        val textView2: TextView = itemView.text_descriptiones
        val textView3: TextView = itemView.text_code
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            val currentItem = exampleList[position]

            if (position != RecyclerView.NO_POSITION) {
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)


    }
}