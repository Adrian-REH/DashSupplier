package com.example.proveedordashboard.fragmentBar

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proveedordashboard.Adapter.AdaptadorCustom
import com.example.proveedordashboard.Model
import com.example.proveedordashboard.RegistroActivity
import com.example.proveedordashboard.databinding.FragmentClientesBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ClientesFragment : Fragment(), AdaptadorCustom.onClienteItemClick {

    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val medio= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/clientesedit.php?proveedorid=
    val medio1= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 116, 111, 107, 101, 110, 105, 100, 61))// /api/clientesedit.php?tokenid=
    val medio2= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 99, 108, 97, 118, 101, 61, 100, 69, 120, 116, 101, 114, 84, 97, 98, 108, 101))// /api/clientesedit.php?clave=dExterTable

    val k0=String(byteArrayOf(85, 82, 76))                                           //URL
    val k2=String(byteArrayOf(83, 116, 114, 105, 110, 103))               //String
    val k3=String(byteArrayOf(100, 97, 116, 97))                                       //data


    val k4=String(byteArrayOf(110, 111, 109, 98, 114, 101))                                       //nombre
    val k5=String(byteArrayOf(110, 109, 97, 113, 117, 105, 110, 97))                             //nmaquina
    val k6=String(byteArrayOf(116, 111, 107, 101, 110, 105, 100))                      //tokenid
    val k7=String(byteArrayOf(70, 101, 99, 104, 97))                                  //Fecha
    val k8=String(byteArrayOf(116, 101, 108, 101, 102, 111, 110, 111))               //telefono

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentClientesBinding.inflate(layoutInflater)

        val arraylis= ArrayList<Model>()
        val displayList= ArrayList<Model>()
        //RECIBO LOS DATOS DEL ACTIVITY PRINCIPAL PARA GENERAR LA LISTA
        val data = arguments
         bind.datoenviado.text = data?.get(k2).toString()
        val id2: String = bind.datoenviado.text.toString()

        val URL= data?.get(k0).toString()


        //GENERA LA LISTA
        var queue = Volley.newRequestQueue(this@ClientesFragment.requireContext())
        var url = "$base$URL$medio$id2"

        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET,url,null,
            { response ->

              try{
                    var jsonArray = response.getJSONArray(k3)

                    for (i in 0 until jsonArray.length()){

                        var jsonObject= jsonArray.getJSONObject(i)

                        val personaName= jsonObject.getString(k4).toString()
                        val maqN= jsonObject.getString(k5).toString()
                        val tid= jsonObject.getString(k6).toString()
                        val Fecha= jsonObject.getString(k7).toString()
                        val cel= jsonObject.getString(k8).toString()
                        arraylis.add(Model(personaName,Fecha,cel,maqN,tid))
                    }

                displayList.addAll(arraylis)
                val AdaptadorCustom= AdaptadorCustom(displayList,this@ClientesFragment.requireContext(),this)
                bind.recyclerView.layoutManager=LinearLayoutManager(this@ClientesFragment.requireContext())
                bind.recyclerView.adapter=AdaptadorCustom



                }catch (e: JSONException){
                    e.printStackTrace()
              }



            }, { error ->

                Toast.makeText(this@ClientesFragment.requireContext(),"$url",Toast.LENGTH_SHORT).show()
                }
            )
        queue.add(jsonObjectRequest)
        //BUSCA EN LA LISTA
        bind.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var id = s.toString()
                val queue = Volley.newRequestQueue(this@ClientesFragment.requireContext())
                val url1 = "$base$URL$medio1$id"
                var jsonObjectRequest= JsonObjectRequest(Request.Method.GET,url1,null,
                    { response ->

                        arraylis.clear()
                        displayList.clear()
                        try{
                            var jsonArray = response.getJSONArray(k3)

                            for (i in 0 until jsonArray.length()){

                                var jsonObject= jsonArray.getJSONObject(i)
                                val personaName= jsonObject.getString(k4).toString()
                                val maqN= jsonObject.getString(k5).toString()
                                val tid= jsonObject.getString(k6).toString()
                                val Fecha= jsonObject.getString(k7).toString()
                                val cel= jsonObject.getString(k8).toString()

                                arraylis.add(Model(personaName,Fecha,cel,maqN,tid))
                            }

                            displayList.addAll(arraylis)

                            val AdaptadorCustom= AdaptadorCustom(displayList,this@ClientesFragment.requireContext(),this@ClientesFragment)
                            bind.recyclerView.layoutManager=LinearLayoutManager(this@ClientesFragment.requireContext())
                            bind.recyclerView.adapter=AdaptadorCustom



                        }catch (e: JSONException){
                            e.printStackTrace()
                        }



                    }, { error ->

                    }
                )
                queue.add(jsonObjectRequest)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        //BUSCA EN LA LISTA



        bind.btnvenc.setOnClickListener {

            val queueB = Volley.newRequestQueue(this@ClientesFragment.requireContext())
            val url1 = "$base$URL$medio$id2"
            val jsonObjectRequestB= JsonObjectRequest(
                Request.Method.GET,url1,null,
                { response ->
                    arraylis.clear()
                    displayList.clear()
                    try{
                        val jsonArray = response.getJSONArray(k3)
                        //DATOS  DE LA FECHA DEL MES QUE VIENE
                        val c = Calendar.getInstance()
                        val day2: Int= c.get(Calendar.DAY_OF_MONTH)
                        var month2: Int= c.get(Calendar.MONTH)
                        var year2: Int= c.get(Calendar.YEAR)
                        var fecha:Int?=null
                        //VERIFICO  QUE LA FECHA DEL MES QUE VIENE TENGA CIERTAS CONDICIONES PARA ESCRIBIRSE A LA VARIABLE QUE LUEGO COMPROBARE
                        if (month2<10){
                            fecha = ("$year2"+"0"+"$month2$day2").toInt()
                            if(day2<10){
                                fecha = ("$year2"+"0"+"$month2"+"0"+"$day2").toInt()
                            }
                        } else if(day2<10){
                            fecha = ("$year2$month2"+"0"+"$day2").toInt()
                        }else{
                            fecha = ("$year2$month2$day2").toInt()
                        }

                        for (i in 0 until jsonArray.length()){
                            val jsonObject= jsonArray.getJSONObject(i)

                            val personaName= jsonObject.getString(k4).toString()
                            val maqN= jsonObject.getString(k5).toString()
                            val tid= jsonObject.getString(k6).toString()
                            val Fecha= jsonObject.getString(k7).toString()
                            val cel= jsonObject.getString(k8).toString()


                            //COMPRUEBO QUE LA FECHA DEL MES QUE VIENE SEA MENOR A LA FECHA DEL CLIENTE PARA QUE PUEDA ASI SABER SI SE VENCIO O NO
                            if ( Fecha.toInt() > fecha){
                                arraylis.add(Model(personaName,Fecha,cel,maqN,tid))
                            }

                        }
                        displayList.addAll(arraylis)
                        val AdaptadorCustom= AdaptadorCustom(displayList,this@ClientesFragment.requireContext(),this)
                        bind.recyclerView.layoutManager= LinearLayoutManager(this@ClientesFragment.requireContext())
                        bind.recyclerView.adapter=AdaptadorCustom

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }



                }, { error ->

                }
            )
            queueB.add(jsonObjectRequestB)



        }






        return bind.root

    }



    override fun onMaquinaItemClick(tokenid: String) {
        val intent = Intent(this@ClientesFragment.requireContext(), RegistroActivity::class.java)
        val data = arguments
        val URL= data?.get(k0).toString()
        intent.putExtra(k6,tokenid)
        intent.putExtra(k0,URL)
        startActivity(intent)
    }



}