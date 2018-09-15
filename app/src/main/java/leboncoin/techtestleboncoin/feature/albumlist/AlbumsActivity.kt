package leboncoin.techtestleboncoin.feature.albumlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_albums.*
import leboncoin.techtestleboncoin.R
import leboncoin.techtestleboncoin.feature.albumlist.repository.AlbumRepositoryProvider

class AlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)


        albumsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

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
                   albumsRecyclerView.adapter = AlbumAdapter(result)

               }, { error ->
                   error.printStackTrace()
               })

    }
}
