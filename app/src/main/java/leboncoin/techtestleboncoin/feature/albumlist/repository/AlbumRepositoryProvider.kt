package leboncoin.techtestleboncoin.feature.albumlist.repository

import leboncoin.techtestleboncoin.network.RestApiService

/**
Singleton repository object
 */
object AlbumRepositoryProvider {
    fun provideAlbumProvider(): AlbumRepository {
        return AlbumRepository(RestApiService.create())
    }
}
