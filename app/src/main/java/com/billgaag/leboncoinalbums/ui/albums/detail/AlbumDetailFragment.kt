package com.billgaag.leboncoinalbums.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoinalbums.R
import com.billgaag.leboncoinalbums.utils.Result
import com.billgaag.leboncoinalbums.utils.setImageUrl
import com.billgaag.leboncoinalbums.utils.showErrorAlert
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.album_detail_fragment.*
import javax.inject.Inject

class AlbumDetailFragment
@Inject
constructor(
        private val viewModel: AlbumDetailViewModel
) : DaggerFragment() {

    var selectedAlbumId: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.album_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.title_album_detail)

        configureViewModel()

        viewModel.getAlbum(selectedAlbumId)
    }

    private fun configureViewModel() {
        viewModel.showedAlbum.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    showAlbum(result.data)
                }
                is Result.Loading -> {
                    showLoading(true)
                }
                is Error -> {
                    showLoading(false)
                    showErrorAlert(result.localizedMessage)
                }
            }
        })
    }

    private fun showLoading(visibility: Boolean) {
        if (visibility) {
            albumIdLayout.visibility = View.INVISIBLE
            albumTitleLabel.visibility = View.INVISIBLE
            albumTitle.visibility = View.INVISIBLE
            albumImage.visibility = View.INVISIBLE

            loader.visibility = View.VISIBLE
        } else {
            albumIdLayout.visibility = View.VISIBLE
            albumTitleLabel.visibility = View.VISIBLE
            albumTitle.visibility = View.VISIBLE
            albumImage.visibility = View.VISIBLE

            loader.visibility = View.INVISIBLE
        }
    }

    private fun showAlbum(album: Album) {
        albumId.text = album.id.toString()
        albumTitle.text = album.title
        albumImage.setImageUrl(album.url)
    }
}
