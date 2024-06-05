package com.example.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceLayout()
        setContentView(R.layout.activity_main)
    }
    private fun replaceLayout(){
        val mainFragment = MainFragment()
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.mainActivity, mainFragment)
        fragmentTrans.commit()
    }
}