package com.project.realtimetrader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.realtimetrader.R
import com.project.realtimetrader.model.Crypto
import com.squareup.picasso.Picasso

class CryptosAdapter (val cryptosAdapterListener: CryptosAdapterListener):
    RecyclerView.Adapter<CryptosAdapter.ViewHolder>() {

    var cryptoList: List<Crypto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crypto = cryptoList[position]

        //Actualizamos los valores de las Cryptomonedas
        Picasso.get()
            .load(crypto.imageUrl)
            .into(holder.image)
        holder.name.text = crypto.name
        holder.available.text = holder.itemView.context.getString(R.string.available_message, crypto.available.toString())
        holder.buyButton.setOnClickListener {

            cryptosAdapterListener.onBuyCryptoClicked(crypto)
        }

    }

    class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        //Mapeamos los componente del "crypto_row"
        val image = view.findViewById<ImageView>(R.id.image)
        var name = view.findViewById<TextView>(R.id.nameTextView)
        var available = view.findViewById<TextView>(R.id.availableTextView)
        var buyButton = view.findViewById<TextView>(R.id.buyButton)
    }
}