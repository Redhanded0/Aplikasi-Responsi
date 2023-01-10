package com.responsi.pcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLogin.setOnClickListener {

            val nim = editTextTextNIM.text.toString().trim()
            val password = editTextTextPassword.text.toString().trim()

            if(nim.isEmpty()){
                editTextTextNIM.error = "Email required"
                editTextTextNIM.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                editTextTextPassword.error = "Password required"
                editTextTextPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.userLogin(nim, password)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t:Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse> ) {
                        if (!response.body()?.error!!) {
                            val intent = Intent(applicationContext, Dashboard::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)
                        }else {
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                })
    }
}