
package com.example.proveedordashboard

import kotlinx.android.synthetic.main.activity_button_navigation_bar.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proveedordashboard.fragmentBar.ClientesFragment
import com.example.proveedordashboard.fragmentBar.DashboardFragment
import com.example.proveedordashboard.fragmentBar.MaquinaFragment
import java.util.ArrayList

class Button_Navigation_Bar : AppCompatActivity(){

    private val dashboardFragment = DashboardFragment() //DASHBOARD
    private val clientesFragment = ClientesFragment()   //CLIENTES
    private val MaquinaFragment = MaquinaFragment()     //MAQUINA
    var idproveedor: TextView?=null                     //TEXTO DEL ID DEL PROVEEDOR
    var URL: String?=null                     //TEXTO DEL ID DEL PROVEEDOR
    var idproveedor2: String?=null                     //TEXTO DEL ID DEL PROVEEDOR
    val k0=String(byteArrayOf(85, 82, 76))                                                                //URL
    val k1=String(byteArrayOf(83, 116, 114, 105, 110, 103))                                              //String
    val k2=String(byteArrayOf(112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100))                    //proveedorid
    val arraylisNCM= ArrayList<String>() //LISTADO DE CANTIDAD DE CLIENTES POR CADA PROVEEDOR EN el MES
    val arraylisM= ArrayList<String>() //LISTADO DE IDENTIFICACION DE MAQUINAS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_navigation_bar)
        //LLAMO AL TEXTO
        idproveedor=findViewById(R.id.idproveedor)


        //CUANDO DOY CLICK AL BOTON DE NAVEGACION ACCIONO
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                R.id.ic_person -> replaceFragment(clientesFragment)
                R.id.ic_maquina -> replaceFragment(MaquinaFragment)
            }
            true
        }

        //MI OTRA ACTIVIDAD ME ENVIA DATOS
        if(intent.extras !=null){
            idproveedor?.setText(intent.getStringExtra(k2).toString()).toString()
            URL = intent.getStringExtra(k0)
            idproveedor2 = intent.getStringExtra(k2).toString()



        }

        BuscMidList()


        //ENVIO DATOS AL FRAGMENT
        val Bundle = Bundle()
        Bundle.putString(k1,idproveedor?.text.toString())
        Bundle.putString(k0,URL.toString())
        Bundle.putString("cantidadM",arraylisM.size.toString())
        Toast.makeText(this," ${arraylisM.size}", Toast.LENGTH_SHORT).show()
        for (i in 0 until arraylisM.size){
            Bundle.putString(i.toString(),arraylisNCM[i])

        }

        clientesFragment.arguments= Bundle
        MaquinaFragment.arguments= Bundle
        dashboardFragment.arguments= Bundle
        replaceFragment(dashboardFragment)

    }



    fun BuscMidList(){
        //Busca las MAQUINA ID en la tabla MAQUINA, GET(PASAN A UNA LISTA)

        val queue = Volley.newRequestQueue(this)
        //1busco las identificaciones de proveedor
        //hago una lista con ellos para luego buscar sus datos uno a uno sin tener que acceder a internet todo el tiempo
        val urlP = "http://$URL/api/maquinaedit.php?proveedorid=${idproveedor2}"
        var jsonObjectRequestP= JsonObjectRequest(
            Request.Method.GET,urlP,null,
            { response ->

                var jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()){

                    var jsonObject= jsonArray.getJSONObject(i)
                    val listmaquinas = jsonObject.getString("maquinaid").toString()
                    arraylisM.add(listmaquinas)

                }


            }, { error ->

            }
        )
        queue.add(jsonObjectRequestP)





    }
    fun NCliMaqList(){
        //Busca los clientes en la tabla CLIENTES con el valor MAQUINA ID, GET(PASAN A UNA LISTA)

        val queue = Volley.newRequestQueue(this)
        for (j in 0 until arraylisM.size){
            val idmc: String = arraylisM[j].toString()
            val urlM = "http://$URL/api/maquinaedit.php?maquinaid=$idmc"
            var jsonObjectRequestM= JsonObjectRequest(
                Request.Method.GET,urlM,null,
                { response ->
                    var jsonArray = response.getJSONArray("data")

                        var jsonObject= jsonArray.getJSONObject(0)
                        val listmaquinas = jsonObject.getString("canclientes").toString()
                        arraylisNCM.add(listmaquinas.toString())



                }, { error ->
                    Toast.makeText(this,"ERROR $error", Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(jsonObjectRequestM)
            try {
                //Ponemos a "Dormir" el programa durante los ms que queremos
                Thread.sleep((2000).toLong())
            } catch (e: Exception) {
                println(e)
            }
        }



    }


    private fun replaceFragment(fragment: Fragment){


        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }




}