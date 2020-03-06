package com.billgaag.leboncoinalbums.ui.albums

import android.os.Bundle
import com.billgaag.leboncoinalbums.R
import com.billgaag.leboncoinalbums.ui.albums.list.ListAlbumFragment
import com.billgaag.leboncoinalbums.ui.list.AlbumDetailFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ListAlbumActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var listAlbumFragment: ListAlbumFragment

    @Inject
    lateinit var albumFragment: AlbumDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.albums_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, listAlbumFragment, listAlbumFragment.javaClass.name)
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
