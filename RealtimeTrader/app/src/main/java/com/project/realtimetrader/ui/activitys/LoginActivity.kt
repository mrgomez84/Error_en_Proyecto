package com.project.realtimetrader.ui.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.realtimetrader.R
import com.project.realtimetrader.model.User
import com.project.realtimetrader.network.Callback
import com.project.realtimetrader.network.FirestoreService
import com.project.realtimetrader.network.USERS_COLLECTION_NAME
import java.lang.Exception

const val USERNAME_KEY = "username_key"

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firestoreService = FirestoreService(FirebaseFirestore.getInstance())
        
    }

    /*Fragmento de codigo hace login pero no guarda el registro del user en la db
    asi quedo en las primeras clases del curso
    abajo se muestra la misma funcion onStartClicked ya con la modificaciones escribiendo
    los datos del user en la db, e incorporando demas funciones en el button y demas*/

    fun onStartClicked(view: View) {
        //En el curso no hay necesidad de declara el obj Edit Text de username
        //Pero en la version actual si no lo declaro genera un bug y no copila
        val username : EditText = findViewById(R.id.usernameTextView)

        view.isEnabled = false
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val username = username.text.toString()
                    firestoreService.findUserById(username, object : Callback<User> {

                        override fun onSuccess(result: User?) {
                            if (result == null) { //significa que el usuario no a sido creado en db
                                val user = User()//vamos a crearlo
                                user.username = username
                                saveUserAndStartMainActivity(user, view)//Lo creamos y guardamos ok
                            } else
                                startMainActivity(username)//Pero si ya fue creado nos vamo a Main
                        }
                        override fun onFailed(exception: Exception) {
                            showErrorMessage(view)
                        }
                    })
                } else {
                    showErrorMessage(view)
                    view.isEnabled = true
                }
            }
    }

    private fun saveUserAndStartMainActivity(user: User, view: View) {
        firestoreService.setDocument(user, USERS_COLLECTION_NAME, user.username, object :
            Callback<Void> {
            override fun onSuccess(result: Void?) {
                startMainActivity(user.username)
            }

            override fun onFailed(exception: Exception) {
                showErrorMessage(view)
                Log.e(TAG, "error", exception)
                view.isEnabled = true
            }
        })
    }

    private fun showErrorMessage(view: View) {
        Snackbar.make(view, getString(R.string.error_while_connecting_to_the_server), Snackbar.LENGTH_LONG)
            .setAction("Info", null).show()
    }

    private fun startMainActivity(username: String) {
        val intent = Intent(this@LoginActivity, TraderActivity::class.java)
        intent.putExtra(USERNAME_KEY, username)
        startActivity(intent)
        finish()
    }

}

