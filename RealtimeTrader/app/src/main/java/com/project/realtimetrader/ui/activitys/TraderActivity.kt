package com.project.realtimetrader.ui.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.realtimetrader.R
import com.project.realtimetrader.adapter.CryptosAdapter
import com.project.realtimetrader.adapter.CryptosAdapterListener
import com.project.realtimetrader.model.Crypto
import com.project.realtimetrader.network.Callback
import com.project.realtimetrader.network.FirestoreService
import java.lang.Exception

class TraderActivity : AppCompatActivity(), CryptosAdapterListener {

    lateinit var firestoreService: FirestoreService//Definimos el servicio

    private val cryptosAdapter: CryptosAdapter = CryptosAdapter(this)//Definimos el Adaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trader)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())//Ya tenemos el servicio
        //Podemos hacer un llamado a nuestras funciones que implementamos anteriormente para la lectura
        //de nuestras cryptomonedas

        configureRecyclerView()
        loadCryptos()

    }

    private fun loadCryptos() {
        firestoreService.getCryptos(object : Callback<List<Crypto>> {
            override fun onSuccess(result: List<Crypto>?) {
                this@TraderActivity.runOnUiThread {
                    cryptosAdapter.cryptoList = result!!
                    cryptosAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailed(exception: Exception) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun configureRecyclerView() {
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = cryptosAdapter
    }

    override fun onBuyCryptoClicked(crypto: Crypto) {//Aqui entra luego de la compra de cryptos
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

