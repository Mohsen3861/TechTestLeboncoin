package leboncoin.techtestleboncoin.network

import io.reactivex.Observable
import leboncoin.techtestleboncoin.constants.Constants
import leboncoin.techtestleboncoin.feature.albumlist.Album
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestApiService {

    @GET("technical-test.json")
    fun getAlbums(): Observable<List<Album>>

    /**
     * Companion object to create the RestApiService
     */
    companion object Factory {
        fun create(): RestApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build()

            return retrofit.create(RestApiService::class.java);
        }
    }
}