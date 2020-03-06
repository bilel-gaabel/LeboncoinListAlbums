package com.billgaag.leboncoinalbums.ui.albums.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoinalbums.R
import com.billgaag.leboncoinalbums.utils.inflate
import com.billgaag.leboncoinalbums.utils.setImageUrl
import kotlinx.android.synthetic.main.album_item.view.*

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    var items = mutableListOf<Album>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((Album) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
            AlbumViewHolder(parent.inflate(R.layout.album_item))

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album: Album = items[position]
        holder.bind(album)
    }

    fun clear() {
        val size = items.size
        items = mutableListOf<Album>()
        notifyItemRangeRemoved(0, size)
    }

    inner class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Album) = with(itemView) {
            albumName.text = item.title
            albumImage.setImageUrl(item.thumbnailUrl)
            itemView.setOnClickListener { itemClickListener?.invoke(item) }
        }
    }
}