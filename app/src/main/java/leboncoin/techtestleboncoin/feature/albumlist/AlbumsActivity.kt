package leboncoin.techtestleboncoin.feature.albumlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import leboncoin.techtestleboncoin.R
import leboncoin.techtestleboncoin.feature.albumlist.repository.AlbumRepositoryProvider

class AlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)


        retrieveAllAlbums()
    }

    /**
    this function retrieves all albums from the API and prints a log with the number of albums available
     */
    private fun retrieveAllAlbums(){

       val repository = AlbumRepositoryProvider.provideAlbumProvider()
       repository.getAlbums()
               .observeOn( AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe ({
                   result ->
                   Log.d("Result", "There are ${result.size} albums")
               }, { error ->
                   error.printStackTrace()
               })

    }
}
