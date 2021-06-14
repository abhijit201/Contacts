package com.nab.contacts.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class Permission {
    fun hasPermissions(context: Context, vararg permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission.toString()
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
}