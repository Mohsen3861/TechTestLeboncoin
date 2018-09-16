package leboncoin.techtestleboncoin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import leboncoin.techtestleboncoin.database.dao.AlbumsDao
import leboncoin.techtestleboncoin.feature.albumlist.Album

@Database(entities = [(Album::class)], version = 1)
abstract class AppDataBase :RoomDatabase(){

    abstract fun albumsDataDao(): AlbumsDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDataBase::class.java, "app.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}