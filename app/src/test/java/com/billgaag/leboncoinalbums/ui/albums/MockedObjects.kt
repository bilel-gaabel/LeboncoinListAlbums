package com.billgaag.leboncoinalbums.ui.albums

import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoinalbums.utils.Result

object MockedObjects {
    var testAlbumId = 1

    val error = Exception("Can't get album or list albums")

    var domainFirstAlbum = Album(
            id = 1,
            albumId = 1,
            title = "title 1",
            url = "url 1",
            thumbnailUrl = "thumbnailUrl 1"
    )
    var domainSecondAlbum = Album(
            id = 2,
            albumId = 2,
            title = "title 2",
            url = "url 2",
            thumbnailUrl = "thumbnailUrl 2"
    )

}