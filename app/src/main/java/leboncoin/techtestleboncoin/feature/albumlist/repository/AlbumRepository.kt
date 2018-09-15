package leboncoin.techtestleboncoin.feature.albumlist.repository

import io.reactivex.Observable
import leboncoin.techtestleboncoin.feature.albumlist.Album
import leboncoin.techtestleboncoin.network.RestApiService

class AlbumRepository( val apiService: RestApiService) {

    /**
    Calls API to retrieve all albums and returns an Observable<List<Album>>
     */
    fun getAlbums(): Observable<List<Album>> {
        return apiService.getAlbums()
    }

}