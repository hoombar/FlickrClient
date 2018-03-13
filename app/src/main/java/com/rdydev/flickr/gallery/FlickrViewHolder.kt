package com.rdydev.flickr.gallery

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rdydev.flickr.gallery.data.model.FlickrItem
import com.squareup.picasso.Picasso

class FlickrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image: ImageView
    val text: TextView

    init {
        image = itemView.findViewById(R.id.flickr_image)
        text = itemView.findViewById(R.id.flickr_text)
    }

    fun loadData(item: FlickrItem) {
        Picasso.get()
                .load(item.media.m)
                .into(image)

        text.setText(item.title)
    }
}