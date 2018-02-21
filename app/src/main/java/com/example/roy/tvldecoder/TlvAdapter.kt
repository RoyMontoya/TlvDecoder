package com.example.roy.tvldecoder

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Roy on 2/19/18.
 */

class TlvAdapter : RecyclerView.Adapter<TlvAdapter.TlvHolder> {

    var tlvList: MutableList<Tlv> = mutableListOf<Tlv>()
    private var context: Context? = null

    constructor(context: Context, elements: MutableList<Tlv>) : super() {
        this.tlvList.addAll(elements)
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TlvHolder {
        val inflatedView = parent.inflate(R.layout.row_tlv, false)
        return TlvHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TlvHolder, position: Int) {
        val tlv = tlvList.get(position)
        holder.bind(tlv)
    }

    override fun getItemCount(): Int {
        return tlvList.size
    }


    class TlvHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v

        val tvTag: TextView
        val tvMeaning: TextView
        val tvValue: TextView

        init {
            this.tvTag = view.findViewById<TextView>(R.id.tv_tag) as TextView
            this.tvMeaning = view.findViewById<TextView>(R.id.tv_meaning) as TextView
            this.tvValue = view.findViewById<TextView>(R.id.tv_value) as TextView
        }

        fun bind(tlv: Tlv) {
            tvTag.text = tlv.tag
            tvMeaning.text = tlv.tagMeaning
            tvValue.text = tlv.value
        }

    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

}