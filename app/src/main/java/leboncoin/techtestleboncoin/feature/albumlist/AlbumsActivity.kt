package leboncoin.techtestleboncoin.feature.albumlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_albums.*
import leboncoin.techtestleboncoin.R
import leboncoin.techtestleboncoin.database.AppDataBase
import leboncoin.techtestleboncoin.feature.albumlist.repository.AlbumRepositoryProvider
import leboncoin.techtestleboncoin.utils.NetworkUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AlbumsActivity : AppCompatActivity() {

    private var mDb: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)


        albumsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        mDb = AppDataBase.getInstance(this)

        if (NetworkUtils.isConnectedToInternet(this)){

            retrieveAllAlbums()

        }else{
            fetchAlbumsDataFromDb()
        }
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

                   insertAlbumsDataInDb(result);
               }, { error ->
                   error.printStackTrace()
               })

    }


    private fun fetchAlbumsDataFromDb() {
       doAsync {
            val albums = mDb?.albumsDataDao()?.getAll()


            uiThread {
                if (albums == null || albums?.size == 0) {
                    Toast.makeText(this@AlbumsActivity,"No albums found !", Toast.LENGTH_SHORT).show()
                } else {
                    albumsRecyclerView.adapter = AlbumAdapter(albums)
                }
            }

        }

    }

    private fun insertAlbumsDataInDb(albums: List<Album>) {
        doAsync {
            mDb?.albumsDataDao()?.insertAll(albums)
        }
    }
}
