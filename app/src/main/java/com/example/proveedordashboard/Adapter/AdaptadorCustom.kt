package com.example.proveedordashboard.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proveedordashboard.Model
import com.example.proveedordashboard.R
import com.example.proveedordashboard.databinding.FragmentClientesBinding
import kotlinx.android.synthetic.main.list_item.view.*

class AdaptadorCustom(val arrayList: ArrayList<Model>, val context: Context, val itemClickListener: onClienteItemClick):
RecyclerView.Adapter<AdaptadorCustom.ViewHolder>(){

    interface onClienteItemClick{
        fun onMaquinaItemClick(tokenid: String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bintItem(arrayList[position],context)
        holder.itemView.setOnClickListener {
            val model=arrayList.get(position)

            var gname:String=model.nombre
            var gtid:String=model.tokenid
            var gfecha:String=model.Fecha
            var gcel:String=model.telefono
            var gmqn:String=model.nmaquina


            val intent=Intent(context,FragmentClientesBinding::class.java)
            intent.putExtra("nombre",gname)
            intent.putExtra("Fecha",gfecha)
            intent.putExtra("telefono",gcel)
            intent.putExtra("nmaquina",gmqn)
            intent.putExtra("tokenid",gtid)

        }
    }
   inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        fun bintItem(model: Model, context: Context){
            itemView.Rlver.setOnClickListener {itemClickListener.onMaquinaItemClick(model.tokenid) }
            itemView.personaName.text=model.nombre
            itemView.tid.text=model.tokenid
            itemView.Fecha.text=model.Fecha
            itemView.cel.text=model.telefono
            itemView.maqN.text=model.nmaquina

        }
    }

}