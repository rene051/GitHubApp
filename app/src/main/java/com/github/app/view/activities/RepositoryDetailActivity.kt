package com.github.app.view.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.github.app.R
import com.github.app.view.BaseActivity


class RepositoryDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)

        supportActionBar!!.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}