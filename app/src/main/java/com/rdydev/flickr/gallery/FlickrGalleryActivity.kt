package com.rdydev.flickr.gallery

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.rdydev.flickr.gallery.data.model.FlickrItem

class FlickrGalleryActivity : Activity(), FlickrView {
    private val TAG = FlickrGalleryActivity::class.java.simpleName

    private lateinit var presenter : FlickrPresenter

    private val adapter = FlickrGalleryAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flickr_gallery)
        presenter = FlickrPresenter(this)

        recyclerView = findViewById(R.id.flickr_images)
        progressBar = findViewById(R.id.loading_spinner)

        recyclerView.layoutManager =  GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchData()
    }

    override fun onData(data: List<FlickrItem>) {
        adapter.setData(data)
    }

    override fun onError(message: String) {
        Log.e(TAG, message);
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

}
