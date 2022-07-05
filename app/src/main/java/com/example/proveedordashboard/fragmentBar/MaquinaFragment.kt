package com.example.proveedordashboard.fragmentBar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proveedordashboard.*
import com.example.proveedordashboard.Adapter.AdapterRecomendado
import com.example.proveedordashboard.databinding.FragmentMaquinaBinding
import kotlinx.android.synthetic.main.fragment_maquina.*
import org.json.JSONException
import java.net.URL

class MaquinaFragment : Fragment(),AdapterRecomendado.onClickListener,
    adaptadorMaquina.onMaquinaItemClick {

    var URL: String?=null  //GUARDO LA URL
    var DATO: String?=null      //GUARDO EL DATO DEL PROVEEDOR ID
    var soport3: Int=0
    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val medio= String(byteArrayOf(47, 97, 112, 105, 47, 118, 109, 97, 113, 117, 105, 110, 97, 101, 100, 105, 116, 46, 112, 104, 112))// /api/vmaquinaedit.php
    val medio1= String(byteArrayOf(47, 97, 112, 105, 47, 109, 97, 113, 117, 105, 110, 97, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/maquinaedit.php?proveedorid=
    val medio2= String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/usuariosedit.php?proveedorid=
    val medio3= String(byteArrayOf(47, 97, 112, 105, 47, 109, 97, 113, 117, 105, 110, 97, 101, 100, 105, 116, 46, 112, 104, 112, 63, 99, 97, 110, 99, 108, 105, 101, 110, 116, 101, 115, 61, 48, 38, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/maquinaedit.php?canclientes=0&proveedorid=

    val k0=String(byteArrayOf(85, 82, 76))                                                      //URL
    val k00=String(byteArrayOf(83, 116, 114, 105, 110, 103))                                                      //String

    val k1=String(byteArrayOf(112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100))                    //proveedorid
    val k2=String(byteArrayOf(109, 97, 113, 117, 105, 110, 97, 105, 100))                            //maquinaid
    val k3=String(byteArrayOf(120, 120))                                                           //xx
    val k4=String(byteArrayOf(115, 116, 111, 99, 107))                                                //stock
    val k5=String(byteArrayOf(99, 112, 117))                                                       //cpu
    val k6=String(byteArrayOf(114, 97, 109))                                                        //ram
    val k7=String(byteArrayOf(114, 101, 100))                                        //red
    val k8=String(byteArrayOf(120, 112, 111, 114, 120))                                                        //xporx
    val k9=String(byteArrayOf(115, 111, 112, 111, 114, 116, 101, 97, 112, 114, 111, 120))                    //soporteaprox
    val k10=String(byteArrayOf(112, 114, 101, 99, 105, 111))                                                  //precio


    val k11=String(byteArrayOf(99, 97, 110, 99, 108, 105, 101, 110, 116, 101, 115))                    //canclientes
    val k12=String(byteArrayOf(115, 111, 112, 111, 114, 116, 97, 100, 111, 115))                      //soportados
    val k13=String(byteArrayOf(102, 101, 99, 104, 97))                                                             //fecha
    val k14=String(byteArrayOf(110, 117, 109, 101, 114, 111))                                             //numero
    val k15=String(byteArrayOf(109, 97, 113, 117, 105, 110, 97, 105, 100))                                    //maquinaid

    val k16=String(byteArrayOf(116, 111, 116, 97, 108, 103, 101, 110, 101, 114, 97, 100, 111))                    //totalgenerado
    val k17=String(byteArrayOf(99, 111, 109, 112, 114, 97))                                             //compra
    val k18=String(byteArrayOf(100, 97, 116, 97))                                                              //data


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //RECIBO LOS DATOS DEL ACTIVITY PRINCIPAL PARA GENERAR DATOS DE LA LISTA MAQUINAS
        val bind = FragmentMaquinaBinding.inflate(layoutInflater)
        val data = arguments
        bind.datoenviado2.text = data?.get(k00).toString()
        DATO=data?.get(k00).toString()
        URL= data?.get(k0).toString()
        val arraylis1= ArrayList<Maquina>()
        val displayList= ArrayList<Maquina>()
        val arraylisr= ArrayList<MaquinaData>()
        val displayListr= ArrayList<MaquinaData>()





        //DATOS DE LA LISTA RECOMENDACIONES DE MAQUINA
        var queuer = Volley.newRequestQueue(this@MaquinaFragment.requireContext())
        var urlr = "$base$URL$medio"
        var jsonObjectRequestr= JsonObjectRequest(
            Request.Method.GET,urlr,null,
            { response ->
                try{
                    var jsonArray = response.getJSONArray(k18)

                    for (i in 0 until jsonArray.length()){

                        var jsonObject= jsonArray.getJSONObject(i)
                        val socket= jsonObject.getString(k4).toString()
                        val cpu= jsonObject.getString(k5).toString()
                        val ram= jsonObject.getString(k6).toString()
                        val red= jsonObject.getString(k7).toString()
                        val xporx= jsonObject.getString(k8).toString()
                        val soport= jsonObject.getString(k9).toString()
                        val precio= jsonObject.getString(k10).toString()
                        arraylisr.add(MaquinaData(socket,cpu,ram,red,xporx,soport,precio))
                    }
                    displayListr.addAll(arraylisr)
                    val adapterRecomendado=AdapterRecomendado(displayListr,this@MaquinaFragment.requireContext(),this)
                    bind.RVRecomendado.layoutManager=LinearLayoutManager(this@MaquinaFragment.requireContext())
                    bind.RVRecomendado.adapter= adapterRecomendado



                }catch (e: JSONException){
                    e.printStackTrace()
                }



            }, { error ->
                Toast.makeText(this@MaquinaFragment.requireContext(),"ERROR $error", Toast.LENGTH_LONG).show()
            }
        )
        queuer.add(jsonObjectRequestr)




        //DATOS DE LA LISTA MAQUINAS PROPIAS
        val id2: String = bind.datoenviado2.text.toString()



        var queue = Volley.newRequestQueue(this@MaquinaFragment.requireContext())
        var url = "$base$URL$medio1$id2"
        var jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                try{
                    var jsonArray = response.getJSONArray(k18)

                    for (i in 0 until jsonArray.length()){

                        var jsonObject= jsonArray.getJSONObject(i)

                        val canclientes= jsonObject.getString(k11).toString()
                        val soportados= jsonObject.getString(k12).toString()
                        val fecha= jsonObject.getString(k13).toString()
                        val numero= jsonObject.getString(k14).toString()
                        val maquinaid= jsonObject.getString(k15).toString()
                        arraylis1.add(Maquina(canclientes,soportados,fecha,numero,maquinaid))


                    }
                    displayList.addAll(arraylis1)
                    val AdaptadorMaquina=
                        adaptadorMaquina(displayList,this@MaquinaFragment.requireContext(),this)
                    bind.RVMaquinas.layoutManager=LinearLayoutManager(this@MaquinaFragment.requireContext())
                    bind.RVMaquinas.adapter= AdaptadorMaquina



                }catch (e: JSONException){
                    e.printStackTrace()
                }



            }, { error ->
            }
        )
        queue.add(jsonObjectRequest)




        //TAREA 7 DEFINO EL CAPITAL GENERADO  PROVEEDOR
        bind.txtliqm.setText("")
        soport3 = 0
        val urlPP = "$base$URL$medio2$id2"
        var jsonObjectRequestPP= JsonObjectRequest(
            Request.Method.GET,urlPP,null,
            { response ->
                var jsonArray = response.getJSONArray(k18)

                    bind.txtliqm.clearComposingText()
                    var jsonObject= jsonArray.getJSONObject(0)
                    val soportados2 = jsonObject.getString(k16).toInt()
                    val soportados3 = jsonObject.getString(k17).toInt()
                    soport3 = soportados2  -soportados3

                bind.txtliqm.text=  "$ $soport3"
                //    bind.txtprmp.text= (soport3/3).toString()
            }, { error ->

            }
        )
        queue.add(jsonObjectRequestPP)


        //TAREA 4 BUSCO EL NUMERO DE MAQUINAS PROPIAS TRABAJANDO
        val urlMP = "$base$URL$medio1$id2"

        val jsonObjectRequestMP= JsonObjectRequest(
            Request.Method.GET,urlMP,null,
            { response ->
                var jsonArray = response.getJSONArray(k18)
                bind.txtocup.text = jsonArray.length().toString()
            }, { error ->
                bind.txtocup.text="0"

            }
        )
        queue.add(jsonObjectRequestMP)


        //TAREA 3 BUSCO EL NUMERO DE MAQUINAS PROPIAS QUE TIENEN CERO CLIENTES
        val urlOM = "$base$URL$medio3$id2"
        val jsonObjectRequestOM= JsonObjectRequest(
            Request.Method.GET,urlOM,null,
            { response ->
                var jsonArray = response.getJSONArray(k18)

                bind.txtdocupadas.text =jsonArray.length().toString()

            }, { error ->
                bind.txtdocupadas.text ="0"

            }
        )
        queue.add(jsonObjectRequestOM)


        return bind.root
    }






    override fun onItemClick(xporx: String) {
        val intent = Intent(this@MaquinaFragment.requireContext(),VentaActivity::class.java)

        intent.putExtra(k3,xporx)
        intent.putExtra(k0,URL)
        intent.putExtra(k1,DATO)
        startActivity(intent)



    }
    override fun onMaquinaItemClick(maquinaid: String) {
        val intent = Intent(this@MaquinaFragment.requireContext(), MaqDataActivity::class.java)

        intent.putExtra(k2,maquinaid)
        intent.putExtra(k0,URL)
        startActivity(intent)
    }
}