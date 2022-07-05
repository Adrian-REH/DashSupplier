package com.example.proveedordashboard.fragmentBar

import android.content.BroadcastReceiver
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proveedordashboard.*
import com.example.proveedordashboard.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.json.JSONException


class DashboardFragment : Fragment() {

    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData
    val arraylisNCM= ArrayList<Int>() //LISTADO DE IDENTIFICACION DE PROVEEDORES
    val arraylisM= ArrayList<String>() //LISTADO DE IDENTIFICACION DE MAQUINAS
    val arraylis= ArrayList<BarEntry>()


    var soport: Int=0
    var soport3: Int=0
    var maquinaid: String=""
    var URL: String=""
    var id2: String=""
    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val medio= String(byteArrayOf(47, 97, 112, 105, 47, 109, 97, 113, 117, 105, 110, 97, 101, 100, 105, 116, 46, 112, 104, 112, 63, 99, 97, 110, 99, 108, 105, 101, 110, 116, 101, 115, 61, 48, 38, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/maquinaedit.php?canclientes=0&proveedorid=
    val medio1= String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/usuariosedit.php?proveedorid=
    val medio2= String(byteArrayOf(47, 97, 112, 105, 47, 109, 97, 113, 117, 105, 110, 97, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/maquinaedit.php?proveedorid=
    val medio3= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/clientesedit.php?proveedorid=
    val medio4= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 109, 97, 113, 117, 105, 110, 97, 105, 100, 61))// /api/clientesedit.php?maquinaid=

    val k0=String(byteArrayOf(85, 82, 76))                                                      //URL
    val k1=String(byteArrayOf(100, 97, 116, 97))                                       //data
    val k2=String(byteArrayOf(116, 111, 116, 97, 108, 103, 101, 110, 101, 114, 97, 100, 111))                                       //totalgenerado
    val k3=String(byteArrayOf(99, 111, 109, 112, 114, 97))                                       //compra
    val k4=String(byteArrayOf(115, 111, 112, 111, 114, 116, 97, 100, 111, 115))                                       //soportados
    val k5=String(byteArrayOf(83, 116, 114, 105, 110, 103))                                       //String
    val k6=String(byteArrayOf(112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100))                                       //proveedorid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentDashboardBinding.inflate(layoutInflater)
        arraylis.clear()

        //Me envian los datos de la actividad principal
            val data = arguments
            bind.datoenviado.text = data?.get(k5).toString()
             URL= data?.get(k0).toString()

            var cantidad= data?.get("cantidadM").toString().toInt()
            for (i in 0 until cantidad){

                arraylis.add(BarEntry(cantidad.toFloat(), data?.get(i.toString()).toString().toFloat()))
            }


        barDataSet = BarDataSet(arraylis, "Clientes en maquina")
        barData= BarData(barDataSet)
      //  bind.barChar.data = barData

        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS, 250)
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 15f






        //bind.txtliq
/*        bind.buttreg1.setOnClickListener {
            val intent = Intent (this@DashboardFragment.requireContext(), RefreshActivity::class.java)
            intent.putExtra(k0,URL)
            intent.putExtra(k6,bind.datoenviado.text.toString())
            startActivity(intent)
        }*/


        //Defino id2 para el dato de la actividad principal y queue para enviar los datos al finalizar la tarea
             id2 = bind.datoenviado.text.toString()
            val queue = Volley.newRequestQueue(this@DashboardFragment.requireContext())








        //TAREA 3 BUSCO EL NUMERO DE MAQUINAS PROPIAS QUE TIENEN CERO CLIENTES
        val urlOM = "$base$URL$medio$id2"
        val jsonObjectRequestOM= JsonObjectRequest(
            Request.Method.GET,urlOM,null,
            { response ->
                var jsonArray = response.getJSONArray(k1)

                bind.txtmaqd.text =jsonArray.length().toString()

            }, { error ->
                bind.txtmaqd.text ="0"

            }
        )
        queue.add(jsonObjectRequestOM)








        //TAREA 7 DEFINO EL CAPITAL GENERADO  PROVEEDOR
        bind.txtliq.setText("")
        soport3 = 0
        val urlPP = "$base$URL$medio1$id2"
        var jsonObjectRequestPP= JsonObjectRequest(
            Request.Method.GET,urlPP,null,
            { response ->
                var jsonArray = response.getJSONArray(k1)

                    bind.txtliq.clearComposingText()
                    var jsonObject= jsonArray.getJSONObject(0)
                    val soportados2 = jsonObject.getString(k2).toInt()
                     val soportados3 = jsonObject.getString(k3).toInt()
                    soport3 = soportados2  -soportados3


                bind.txtliq.text=  "$ $soport3"
                //    bind.txtprmp.text= (soport3/3).toString()
            }, { error ->

            }
        )
        queue.add(jsonObjectRequestPP)


        //TAREA 4 BUSCO EL NUMERO DE MAQUINAS PROPIAS TRABAJANDO
        val urlMP = "$base$URL$medio2$id2"
        val jsonObjectRequestMP= JsonObjectRequest(
            Request.Method.GET,urlMP,null,
            { response ->
                var jsonArray = response.getJSONArray(k1)
                bind.txtmaqt.text = jsonArray.length().toString()
            }, { error ->


            }
        )
        queue.add(jsonObjectRequestMP)


        //TAREA1 BUSCO EL NUMERO DE CLIENTES con jsonARRAY
            val url1 = "$base$URL$medio3$id2"
            var jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET,url1,null,
                { response ->
                    var jsonArray = response.getJSONArray(k1)
                    bind.txtcli.text =jsonArray.length().toString()
                }, { error ->

                }
            )
            queue.add(jsonObjectRequest)


        //TAREA 2 BUSCO EL NUMERO DE MAQUINAS y SUMO LA CANTIDAD QUE SOPORTA con jsonARRAY

            soport = 0
            val url = "$base$URL$medio2$id2"
            var jsonObjectRequest1= JsonObjectRequest(
                Request.Method.GET,url,null,
                { response ->
                    var jsonArray = response.getJSONArray(k1)
                    for (i in 0 until jsonArray.length()){
                        bind.txtcsop.clearComposingText()
                        var jsonObject= jsonArray.getJSONObject(i)
                        val soportados = jsonObject.getString(k4).toInt()
                        soport = soportados + soport
                    }
                    bind.txtcsop.text=  soport.toString()

                }, { error ->

                }
            )
            queue.add(jsonObjectRequest1)




/*


*/



        return bind.root
    }

    fun BuscMidList(){





    }
    fun NCliMaqList(){
        //Busca los clientes en la tabla CLIENTES con el valor MAQUINA ID, GET(PASAN A UNA LISTA)
        BuscMidList()
        val queue = Volley.newRequestQueue(this@DashboardFragment.requireContext())


       //val arrayAdapter = ArrayAdapter(this,R.layout.list_item_drop,arraylisNCM)
      //  TxtVDrop?.setAdapter(arrayAdapter)


    }

}