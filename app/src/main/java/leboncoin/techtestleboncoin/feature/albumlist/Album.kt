package leboncoin.techtestleboncoin.feature.albumlist

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/*
this data class will contain some informations about an album
 */
@Entity(tableName = "albums")
data class Album (
        @PrimaryKey val id:Long,
        @ColumnInfo(name = "albumId") val albumId:Long,
        @ColumnInfo(name = "title") val title:String,
        @ColumnInfo(name = "url") val url:String,
        @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl:String
)

