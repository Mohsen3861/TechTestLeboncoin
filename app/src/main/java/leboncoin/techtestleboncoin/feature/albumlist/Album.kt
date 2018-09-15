package leboncoin.techtestleboncoin.feature.albumlist

/*
this data class will contain some informations about an album
 */
data class Album (
        val albumId:Long,
        val id:Long,
        val title:String,
        val url:String,
        val thumbnailUrl:String
)

