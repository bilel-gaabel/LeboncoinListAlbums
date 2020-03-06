package com.billgaag.leboncoin.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.billgaag.leboncoin.data.source.local.dao.AlbumDao
import com.billgaag.leboncoin.data.source.local.entity.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AlbumsDataBase : RoomDatabase() {

    abstract fun albumsDao(): AlbumDao

    companion object {
        private var INSTANCE: AlbumsDataBase? = null

        fun getInstance(context: Context): AlbumsDataBase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, AlbumsDataBase::class.java, "AlbumsDataBase")
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
            }

            return instance
        }
    }
}