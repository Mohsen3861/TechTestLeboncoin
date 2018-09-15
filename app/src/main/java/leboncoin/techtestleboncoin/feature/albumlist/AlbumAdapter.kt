package leboncoin.techtestleboncoin.feature.albumlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import leboncoin.techtestleboncoin.R

class AlbumAdapter(val albumList: List<Album>): RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.albumTitle?.text = albumList[position].title

        Picasso.get()
                .load(albumList[position].thumbnailUrl)
                .resize(50, 50)
                .centerCrop()
                .into(holder.albumImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.albums_list_item, parent, false)
        return ViewHolder(v);

    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val albumTitle = itemView.findViewById<TextView>(R.id.titleTextView)
        val albumImage = itemView.findViewById<ImageView>(R.id.albumImageView)

    }

}