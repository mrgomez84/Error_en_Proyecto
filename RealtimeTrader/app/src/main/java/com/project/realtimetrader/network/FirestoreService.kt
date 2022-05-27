package com.project.realtimetrader.network

import com.google.firebase.firestore.FirebaseFirestore
import com.project.realtimetrader.model.Crypto

import com.project.realtimetrader.model.User

const val CRYPTO_COLLECTION_NAME = "cryptos"
const val USERS_COLLECTION_NAME = "user"

class FirestoreService (val firebaseFirestore: FirebaseFirestore) {

    //Funcion para guardar cualquier tipo de documento en Firestore de namera generica
    fun setDocument(data: Any, collectionName: String, id: String, callback: Callback<Void>){

        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }
    //Funcion para actualizar el usuario
    fun updateUser(user: User, callback: Callback<User>?){
        firebaseFirestore.collection(USERS_COLLECTION_NAME).document(user.username)
            .update("cryptosList", user.cryptosList) //<------------------------------------------ es cryptosList revisar en User.kt
            .addOnSuccessListener { result ->
                if (callback != null)
                    callback.onSuccess(user)
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception) }
    }
    //Funcion para actualizar las crypto
    fun updateCrypto(crypto: Crypto){
        firebaseFirestore.collection(CRYPTO_COLLECTION_NAME).document(crypto.getDocuemntId())//<------------------------------------------ es getDocumentId
            .update("available", crypto.available)
    }

    //Funcion que consulte toda la lisa de crypto monedas, esta función hace la lectura de cryto monedas en db
    fun getCryptos(callback: Callback<List<Crypto>>?){
        firebaseFirestore.collection(CRYPTO_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    val cryptoList = result.toObjects(Crypto::class.java)
                    callback!!.onSuccess(cryptoList)
                    break
                }
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception)}
    }


    //Funcion que encuentre los usuario deacuerdo al id
    fun findUserById(id: String, callback: Callback<User>) {
        firebaseFirestore.collection(USERS_COLLECTION_NAME).document(id)
            .get()
            .addOnSuccessListener { result ->
                if (result.data != null) {
                    callback.onSuccess(result.toObject(User::class.java))
                } else {
                    callback.onSuccess(null)
                }
            }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }
}