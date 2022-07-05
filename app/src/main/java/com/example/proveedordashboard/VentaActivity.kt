package com.example.proveedordashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*
import kotlin.collections.HashMap

class VentaActivity : AppCompatActivity() {
    var Paytxtstock: TextView?=null       // PRESIO QUE LE ASGINO A CADA MAQUINA DE FORMA CALCULADA
    var Paytxtprecio: TextView?=null         // USUARIO
    var Paytxtproveedor: TextView?=null         //PROVEEDOR ID
    var txtred: TextView?=null         //RED
    var txtram: TextView?=null         //RAM
    var txtcpu: TextView?=null         //CPU
    var txtsop: TextView?=null         //SOPORTADO
    var Paytxtcompras: EditText?=null         //
    var Paytxtcantidad: EditText?=null         //
    var txtxporx: TextView?=null         //
    var URL:String?=null      //
    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val mediocompra= String(byteArrayOf(47, 97, 112, 105, 47, 99, 111, 109, 112, 114, 97, 115, 101, 100, 105, 116, 46, 112, 104, 112))// /api/comprasedit.php
    val mediogenerar2= String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61)) // /api/usuariosedit.php?proveedorid=
    val mediogenerar= String(byteArrayOf(47, 97, 112, 105, 47, 118, 109, 97, 113, 117, 105, 110, 97, 46, 112, 104, 112, 63, 120, 112, 111, 114, 120, 61)) // /api/vmaquina.php?xporx=
    var DATO:String?=null      //
    var soport3:Int=0      //
    var soport2:Int=0      //
    var valorfinal:Int=0      //
    var stock:Int=0      //
    var Fecha:String="0"      //

    //keys
    val k0=String(byteArrayOf(112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100))         //proveedorid
    val k1=String(byteArrayOf(120, 120))                                                       //xx
    val k2=String(byteArrayOf(85, 82, 76))                                                     //URL
    val k3=String(byteArrayOf(99, 112, 117))                                                   //cpu
    val k4=String(byteArrayOf(114, 97, 109))                                                   //ram
    val k5=String(byteArrayOf(114, 101, 100))                                                  //red
    val k6=String(byteArrayOf(115, 116, 111, 99, 107))                                          //stock
    val k7=String(byteArrayOf(115, 111, 112, 111, 114, 116, 101, 97, 112, 114, 111, 120))      //soporteaprox
    val k8=String(byteArrayOf(112, 114, 101, 99, 105, 111))                                    //precio
    val k9=String(byteArrayOf(100, 97, 116, 97))                                               //data
    val k10=String(byteArrayOf(116, 111, 116, 97, 108, 103, 101, 110, 101, 114, 97, 100, 111)) //totalgenerado
    val k11=String(byteArrayOf(99, 111, 109, 112, 114, 97))                                    //compra
    val k12=String(byteArrayOf(120, 112, 111, 114, 120))                                       //xporx
    val k13=String(byteArrayOf(103, 97, 115, 116, 97, 100, 111))                               //gastado
    val k14=String(byteArrayOf(102, 101, 99, 104, 97))                                          //fecha

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venta)
        Paytxtstock=findViewById(R.id.Paytxtstock)                 // PRESIO QUE LE ASGINO A CADA MAQUINA DE FORMA CALCULADA
        Paytxtproveedor=findViewById(R.id.Paytxtproveedor)         // PRESIO QUE LE ASGINO A CADA MAQUINA DE FORMA CALCULADA
        Paytxtprecio=findViewById(R.id.Paytxtprecio)               // USUARIO
        Paytxtcantidad=findViewById(R.id.Paytxtcantidad)           //
        txtred=findViewById(R.id.txtred)                           //
        txtram=findViewById(R.id.txtram)                           //
        txtcpu=findViewById(R.id.txtcpu)                           //
        txtsop=findViewById(R.id.txtsoport)                        //
        Paytxtcompras=findViewById(R.id.Paytxtcompras)             //
        txtxporx=findViewById(R.id.txtxporx)                       // BOTON PARA VENDER


        Paytxtcantidad?.setText("1")


        if(intent.extras !=null){
            DATO = intent.getStringExtra(k0).toString()
            txtxporx?.setText(intent.getStringExtra(k1).toString())
            URL = intent.getStringExtra(k2).toString()
            Generar()


        }
    }




    fun Generar(){
        val id2: String = DATO.toString()
        val queue = Volley.newRequestQueue(this)
        //GENERO LOS DATOS DE LA MAQUINA
        val id =txtxporx?.text.toString()
        val url = "$base$URL$mediogenerar$id"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                txtcpu?.setText(response.getString(k3))
                txtram?.setText(response.getString(k4))
                txtred?.setText(response.getString(k5))
                Paytxtstock?.setText(response.getString(k6))
                txtsop?.setText(response.getString(k7))
                Paytxtprecio?.setText("$"+response.getString(k8))

            }, { error ->
                Toast.makeText(this,error.toString(), Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
        // DEFINO EL CAPITAL TOTAL DEL PROVEEDOR
        Paytxtproveedor?.setText("")
        Paytxtcompras?.setText("")
        soport3 = 0
        soport2 = 0
        val urlPP = "$base$URL$mediogenerar2$id2"
        var jsonObjectRequestPP= JsonObjectRequest(
            Request.Method.GET,urlPP,null,
            { response ->
                var jsonArray = response.getJSONArray(k9)
                Paytxtproveedor?.clearComposingText()
                Paytxtcompras?.clearComposingText()
                var jsonObject= jsonArray.getJSONObject(0)
                val soportados2 = jsonObject.getString(k10).toInt()
                val soportados3 = jsonObject.getString(k11).toInt()
                Paytxtproveedor?.text=   (soportados2 - soportados3).toString()
                Paytxtcompras?.setText("$soportados3")
            }, { error ->
                    // MENSAJE DEL SERVIDOR
            }
        )
        queue.add(jsonObjectRequestPP)



    }


    fun FechaCompra(){
        val c = Calendar.getInstance()
        val day: Int= c.get(Calendar.DAY_OF_MONTH)
        var month: Int= c.get(Calendar.MONTH) + 1
        var year: Int= c.get(Calendar.YEAR)
        if(month.equals(13)){
            month = 1
            year += 1
            var fecha:Int = ("$year"+"0"+"$month$day").toInt()
            Fecha=("$fecha")
        }else if (month<10){
            var fecha:Int = ("$year"+"0"+"$month$day").toInt()
            Fecha=("$fecha")
        }else{Fecha=("$year$month$day")}
    }


    fun ClickComprar(view: View){
        FechaCompra()
        valorfinal= Paytxtproveedor?.text.toString().toInt() - Paytxtprecio?.text.toString().toInt()*Paytxtcantidad?.text.toString().toInt()
        val gastado = Paytxtprecio?.text.toString().toInt()*Paytxtcantidad?.text.toString().toInt()
        val valordecompra = Paytxtcompras?.text.toString().toInt() + gastado
        stock =Paytxtstock?.text.toString().toInt() - Paytxtcantidad?.text.toString().toInt()
        if(valorfinal<0){
            Toast.makeText(this,"Saldo insuficiente", Toast.LENGTH_LONG).show()
        }else if(stock<0){
            Toast.makeText(this,"No hay suficiente stock para la cantidad que desea comprar", Toast.LENGTH_LONG).show()
        }else{
            //EDITO EL VALOR DE COMPRA QUE POSEE EL PROVEEDOR
            val url = "$base$URL$mediocompra"
            val queue= Volley.newRequestQueue(this)

            var resultadoPost = object : StringRequest(Method.POST,url,
                Response.Listener<String> { response ->

                }, Response.ErrorListener { error ->
                    Toast.makeText(this,"ERROR $error", Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String,String>()
                    parametros.put(k11,valordecompra.toString())
                    parametros.put(k0,DATO.toString())
                    parametros.put(k12,txtxporx?.text.toString())
                    parametros.put(k6,stock.toString())
                    parametros.put(k13,gastado.toString())
                    parametros.put(k14,Fecha)
                    return parametros
                }
            }
            queue.add(resultadoPost)
        }
    finish()
    }
}