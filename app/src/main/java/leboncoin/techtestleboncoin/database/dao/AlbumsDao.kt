package leboncoin.techtestleboncoin.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import leboncoin.techtestleboncoin.feature.albumlist.Album

@Dao
interface AlbumsDao {

    @Query("SELECT * from albums")
    fun getAll(): List<Album>

    @Insert(onConflict = REPLACE)
    fun insert(album: Album)

    @Insert(onConflict = REPLACE)
    fun insertAll(albums : List<Album>)

    @Query("DELETE from albums")
    fun deleteAll()


}