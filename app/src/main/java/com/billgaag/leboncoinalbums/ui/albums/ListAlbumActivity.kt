package com.billgaag.leboncoinalbums.ui.albums

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.billgaag.leboncoinalbums.R
import com.billgaag.leboncoinalbums.ui.albums.list.ListAlbumFragment

class ListAlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.albums_activity)

        if (savedInstanceState == null) {
            val listAlbumFragment = ListAlbumFragment.newInstance(this)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, listAlbumFragment)
                    .commitNow()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 1) {
            supportFragmentManager.beginTransaction()
                    .remove(supportFragmentManager.fragments.last()).commitNow()
        } else {
            super.onBackPressed()
        }
    }

}
