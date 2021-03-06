package com.impinity.verdict

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater

class MainActivity : AppCompatActivity() {
    val trans = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        trans.beginTransaction()
            .replace(R.id.fragment_container, homeFragment, "HomeFragment")
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.trial_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val homeFragment = HomeFragment()
        trans.beginTransaction()
            .replace(R.id.fragment_container, homeFragment, "HomeFragment")
            .commit()
        return true
    }
}
