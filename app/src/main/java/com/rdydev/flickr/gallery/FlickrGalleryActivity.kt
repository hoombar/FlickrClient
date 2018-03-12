package com.rdydev.flickr.gallery

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.rdydev.flickr.gallery.data.model.FlickrItem
import flickr.rdydev.com.flickrgallery.R

class FlickrGalleryActivity : Activity(), FlickrView {
    private val TAG = FlickrGalleryActivity::class.java.simpleName

    private lateinit var presenter : FlickrPresenter

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flickr_gallery)
        presenter = FlickrPresenter(this)

        recyclerView = findViewById(R.id.flickr_images)
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchData()
    }

    override fun onData(data: List<FlickrItem>) {
        // TODO(benp) implement adapter
    }

    override fun onError(message: String) {
        Log.e(TAG, message);
    }

}
