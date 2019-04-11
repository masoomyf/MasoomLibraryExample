package com.masoomyf.masoom

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.support.annotation.LayoutRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.io.File

/**
 * MasoomLibraryExample is created by masoomyf on 11/04/2019.
 */
const val RC_GALLERY_PERMISSION = 12131
const val RC_GALLERY = 12132

object Masoom {
    var tag = "MASOOM"
    fun logd(message: String) {
        Log.d(tag, message)
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun selectImageFromGallery(activity: Activity) {
        if (!readPermissionGranted(activity)) {
            requestReadWritePermission(activity)
        } else {
            openGallery(activity)
        }
    }

    fun openGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(Intent.createChooser(intent, "Select a photo"), RC_GALLERY)
    }

    fun requestReadWritePermission(activity: Activity) {
        ActivityCompat.requestPermissions(activity, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE), RC_GALLERY_PERMISSION)
    }

    fun readPermissionGranted(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
            return true
        return false
    }

    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
        if (requestCode == RC_GALLERY_PERMISSION) {
            return if (grantResults.size == 2 && grantResults[0] == grantResults[1] && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImageFromGallery(activity)
                true
            } else {
                logd("Permission not granted.")
                false
            }
        }

        return false
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Uri? {
        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                return data?.data
            }
        }

        return null
    }

    fun inflate(parent: ViewGroup, @LayoutRes layoutResource: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
    }

    fun addToGallery(context: Context, filName: String) {
        MediaScannerConnection.scanFile(context, arrayOf(filName), null) { _, _ -> }
    }

    fun sharePhoto(context: Context, photoUri: Uri) {
        val fileUri = FileProvider.getUriForFile(context,
                context.packageName, File(photoUri.path))
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${context.packageName}")
            putExtra(Intent.EXTRA_STREAM, fileUri)
            type = "*/*"
        }
        context.startActivity(shareIntent)
    }


}