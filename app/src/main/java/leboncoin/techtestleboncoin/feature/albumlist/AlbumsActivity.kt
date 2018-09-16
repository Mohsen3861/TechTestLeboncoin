package leboncoin.techtestleboncoin.feature.albumlist

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
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


        //defining the direction of the RecyclerView
        albumsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //initializing our local DB
        mDb = AppDataBase.getInstance(this)


        pupulateList();

        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent));

        //listener for pull to refresh
        swipeRefreshLayout.setOnRefreshListener {

            this@AlbumsActivity.pupulateList()

        }

    }


    /**
    this function populates our recyclerView
     */
   fun pupulateList(){
       //checking if there is any internet connection
       if (NetworkUtils.isConnectedToInternet(this)){

           //fetch albums from API
           retrieveAllAlbums()

       }else{
           Toast.makeText(this@AlbumsActivity,getString(R.string.no_internet_message) , Toast.LENGTH_LONG).show()

           //fetch albums from DB
           fetchAlbumsDataFromDb()
       }

   }

    /**
    this function retrieves all albums from the API and changes the recyclerview's adapter
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
               },{
                   //hide the loading animation
                   swipeRefreshLayout.isRefreshing = false
               })

    }


    /**
    this function retrieves all albums from the DB and changes the recyclerview's adapter
     */
    private fun fetchAlbumsDataFromDb() {

       doAsync {
            //calling DB to retrieve all albums
            val albums = mDb?.albumsDataDao()?.getAll()


            uiThread {

                //no albums found
                if (albums == null || albums?.size == 0) {
                    Toast.makeText(this@AlbumsActivity,getString(R.string.no_albums), Toast.LENGTH_SHORT).show()
                } else {
                    albumsRecyclerView.adapter = AlbumAdapter(albums)
                }

                //hide the loading animation
                swipeRefreshLayout.isRefreshing = false
            }

        }

    }

    /**
     * this function inserts a list of albums into the DB
     */
    private fun insertAlbumsDataInDb(albums: List<Album>) {
        doAsync {

            //calling DB to insert albums
            mDb?.albumsDataDao()?.insertAll(albums)
        }
    }
}
