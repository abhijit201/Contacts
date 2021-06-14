package com.nab.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.nab.contacts.util.Permission

class SplashScreen : AppCompatActivity() {

    private val ALL_PERMISSIONS = 101
    val permissions = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if (!Permission().hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(mainActivityIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ALL_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val mainActivityIntent = Intent(this, MainActivity::class.java)
                    mainActivityIntent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(mainActivityIntent)
                } else {
                    Toast.makeText(
                        this,
                        "PERMISSIONS Denied, please accept",
                        Toast.LENGTH_LONG
                    ).show()
                    ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
                }
            }
        }
    }
}