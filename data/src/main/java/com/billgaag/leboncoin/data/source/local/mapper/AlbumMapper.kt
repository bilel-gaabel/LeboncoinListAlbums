package com.billgaag.leboncoin.data.source.local.mapper

import com.billgaag.leboncoin.domain.model.Album

typealias AlbumEntity = com.billgaag.leboncoin.data.source.local.entity.Album

fun Album.asEntity() = AlbumEntity(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
)

fun AlbumEntity.asDomain() = Album(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
)

fun List<Album>.asEntity(): List<AlbumEntity> = map(Album::asEntity)

fun List<AlbumEntity>.asDomain(): List<Album> = map(AlbumEntity::asDomain)