package com.gsamrua.firebasemessagin

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val first = intent.getStringExtra("first")

        setTheme(R.style.Theme_Transparent)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.item)

        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")

        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(applicationContext, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }

        val dialog = builder.create()
        dialog.show()
    }
}
