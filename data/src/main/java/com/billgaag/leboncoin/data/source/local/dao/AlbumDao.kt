package com.billgaag.leboncoin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.billgaag.leboncoin.data.source.local.entity.Album
import io.reactivex.Single

@Dao
interface AlbumDao {
    @Query(value = "SELECT * FROM album_table WHERE id = :id")
    fun getAlbum(id: Int): Single<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Album>)

    @Query("SELECT * FROM album_table")
    fun getAll(): Single<List<Album>>
}