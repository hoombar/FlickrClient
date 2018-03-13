package com.rdydev.flickr.gallery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rdydev.flickr.gallery.data.model.FlickrItem

class FlickrGalleryAdapter : RecyclerView.Adapter<FlickrViewHolder>() {

    private var data: List<FlickrItem> = listOf()

    fun setData(data: List<FlickrItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        holder.loadData(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_adapter_item, parent, false)

        return FlickrViewHolder(view)
    }
}