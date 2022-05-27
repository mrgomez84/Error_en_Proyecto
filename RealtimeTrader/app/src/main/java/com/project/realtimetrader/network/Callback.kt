package com.project.realtimetrader.network

import java.lang.Exception

interface Callback<T>{

    fun onSuccess(result: T?) //Si es correcta llama una funcion "sobre el exito"

    fun onFailed(exception: Exception) //Si falla llamamos una funcion "en fallo" y llamara una exception
}