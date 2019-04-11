package com.masoomyf.masoomlibraryexample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.masoomyf.masoom.Masoom

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Masoom.selectImageFromGallery(this)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Masoom.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Masoom.onActivityResult(requestCode, resultCode, data)
    }
}
