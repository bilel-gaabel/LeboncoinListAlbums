package com.billgaag.leboncoin.data.source.remote.mapper

import com.billgaag.leboncoin.domain.model.Album

typealias RemoteAlbum = com.billgaag.leboncoin.data.source.remote.model.Album

fun Album.asRemote() = RemoteAlbum(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
)

fun RemoteAlbum.asDomain() = Album(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
)

fun List<Album>.asRemote(): List<RemoteAlbum> = map(Album::asRemote)

fun List<RemoteAlbum>.asDomain(): List<Album> = map(RemoteAlbum::asDomain)
