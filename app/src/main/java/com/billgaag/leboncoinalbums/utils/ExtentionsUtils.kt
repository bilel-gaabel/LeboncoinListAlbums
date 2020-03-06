package com.billgaag.leboncoinalbums.utils

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.billgaag.leboncoinalbums.R
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment


fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.setImageUrl(strTeamBadge: String?) {
    Picasso.get()
            .load(strTeamBadge)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)
            .into(this)

}

fun RecyclerView.addLoadMoreListener(loadMore: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy <= 0) return

            val lastVisibleItem =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            if ((layoutManager?.itemCount ?: 0) <= lastVisibleItem + Constants.SHOWED_PAGE_SIZE) {
                loadMore.invoke()
            }
        }
    })
}

fun DaggerFragment.showErrorAlert(message: String?) {
    val alertDialog = AlertDialog.Builder(context)

    alertDialog.setTitle(getString(R.string.error))
    alertDialog.setMessage(message)
    alertDialog.setNeutralButton(
            getString(R.string.ok)
    ) { dialog, _ -> dialog.dismiss() }

    alertDialog.show()
}