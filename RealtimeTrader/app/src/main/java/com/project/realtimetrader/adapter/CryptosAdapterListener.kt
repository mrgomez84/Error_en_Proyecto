package com.project.realtimetrader.adapter

import com.project.realtimetrader.model.Crypto

interface CryptosAdapterListener{

    fun onBuyCryptoClicked(crypto: Crypto)
}