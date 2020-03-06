package com.billgaag.leboncoinalbums.ui.albums.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.billgaag.leboncoinalbums.R
import com.billgaag.leboncoinalbums.ui.list.AlbumDetailFragment
import com.billgaag.leboncoinalbums.utils.Result.Loading
import com.billgaag.leboncoinalbums.utils.Result.Success
import com.billgaag.leboncoinalbums.utils.addLoadMoreListener
import com.billgaag.leboncoinalbums.utils.showErrorAlert
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_albums_fragment.*
import javax.inject.Inject

class ListAlbumFragment
@Inject
constructor(
        private val viewModel: ListAlbumViewModel
) : DaggerFragment() {

    @Inject
    lateinit var albumFragment: AlbumDetailFragment

    private val adapter by lazy { AlbumAdapter() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_albums_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_albums)

        initViews()
        configureViewModel()

        viewModel.getListAlbum()
    }

    private fun initViews() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        adapter.itemClickListener = { album ->
            goToDetailAlbum(album.id)
        }

        recyclerView.addLoadMoreListener {
            viewModel.loadNextPageList(adapter.items.size)
        }

        swipeRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.getListAlbum()
        }
    }

    private fun configureViewModel() {
        viewModel.showedAlbumList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Success -> {
                    swipeRefresh.isRefreshing = false
                    val lastItemPos = adapter.items.size
                    adapter.items.addAll(result.data)
                    adapter.notifyItemRangeInserted(lastItemPos, adapter.items.size)
                }
                is Loading -> {
                    swipeRefresh.isRefreshing = true
                }
                is Error -> {
                    swipeRefresh.isRefreshing = false
                    showErrorAlert(result.localizedMessage)
                }
            }
        })
    }

    private fun goToDetailAlbum(idAlbum: Int) {
        activity?.let {
            albumFragment.selectedAlbumId = idAlbum

            it.supportFragmentManager.beginTransaction()
                    .add(R.id.container, albumFragment, albumFragment.javaClass.name)
                    .commitNow()
        }
    }
}
