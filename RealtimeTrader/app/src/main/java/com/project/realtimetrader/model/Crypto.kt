package com.project.realtimetrader.model

class Crypto  (var name: String = "", var imageUrl: String = "", var available: Int = 0){
    fun getDocuemntId(): String{
        return name.toLowerCase()
    }
}