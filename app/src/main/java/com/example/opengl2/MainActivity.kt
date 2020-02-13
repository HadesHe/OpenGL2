package com.example.opengl2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opengl2.view.GL2View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GL2View(this))
    }
}
