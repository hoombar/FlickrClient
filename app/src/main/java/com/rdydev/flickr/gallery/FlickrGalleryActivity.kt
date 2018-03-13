package com.rdydev.flickr.gallery

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import com.rdydev.flickr.gallery.data.model.FlickrItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class FlickrGalleryActivity : Activity(), FlickrView {
    private val TAG = FlickrGalleryActivity::class.java.simpleName

    private lateinit var presenter : FlickrPresenter

    private val adapter = FlickrGalleryAdapter()
    private lateinit var dataContainer: ViewGroup
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar : ProgressBar
    private lateinit var searchText: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flickr_gallery)
        presenter = FlickrPresenter(this)

        bindViews()

        recyclerView.layoutManager = GridLayoutManager(this, 2)!!
        recyclerView.adapter = adapter

        setupSearch()
    }

    private fun setupSearch() {
        val publishSubject : PublishSubject<String> = PublishSubject.create()

        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                publishSubject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    publishSubject.onNext(newText)
                }
                return true
            }
        })

        publishSubject
                .debounce(200, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .map { it.replace(" ", "") }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchString -> presenter.searchTags(searchString) })
    }

    private fun bindViews() {
        dataContainer = findViewById(R.id.flickr_data_container)
        recyclerView = findViewById(R.id.flickr_images)
        progressBar = findViewById(R.id.loading_spinner)
        searchText = findViewById(R.id.flickr_search)
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
        dataContainer.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        dataContainer.visibility = View.VISIBLE
    }

}
