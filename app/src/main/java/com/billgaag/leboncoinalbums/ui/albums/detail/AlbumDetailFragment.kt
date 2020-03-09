package com.billgaag.leboncoinalbums.ui.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoinalbums.R
import com.billgaag.leboncoinalbums.utils.Result
import com.billgaag.leboncoinalbums.utils.setImageUrl
import com.billgaag.leboncoinalbums.utils.showErrorAlert
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.album_detail_fragment.*
import javax.inject.Inject

class AlbumDetailFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    lateinit var viewModel: AlbumDetailViewModel

    companion object {
        private const val ALBUM_ID_ARG = "ALBUM_ID_ARG"

        fun newInstance(activity: Activity, albumId: Int): Fragment {
            return FragmentFactory.loadFragmentClass(
                activity.classLoader,
                AlbumDetailFragment::class.java.name
            )
                .newInstance()
                .apply {
                    arguments = Bundle().apply { putInt(ALBUM_ID_ARG, albumId) }
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, vmFactory).get(AlbumDetailViewModel::class.java)

    }

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

        if (savedInstanceState == null) {
            val selectedAlbumId = arguments?.getInt(ALBUM_ID_ARG) ?: 0

            viewModel.getAlbum(selectedAlbumId)
        }
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
