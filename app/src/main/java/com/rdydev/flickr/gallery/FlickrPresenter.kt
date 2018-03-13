package com.rdydev.flickr.gallery

import com.rdydev.flickr.gallery.data.DataModule
import com.rdydev.flickr.gallery.data.DataSorter
import com.rdydev.flickr.gallery.data.FlickrApi
import com.rdydev.flickr.gallery.data.model.FlickrItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class FlickrPresenter {

    private var view: FlickrView
    private val flickrApi: FlickrApi
    private val dataSorter: DataSorter

    constructor(view: FlickrView) : this(view, DataModule.provideFlickrApi(), DataModule.provideDataSorter())

    constructor(view: FlickrView, flickrApi: FlickrApi, dataSorter: DataSorter) {
        this.view = view
        this.flickrApi = flickrApi
        this.dataSorter = dataSorter
    }

    fun fetchData() {
        view.showLoading()

        watch(flickrApi.getFeed())
    }

    fun searchTags(tags: String?) {
        if (tags.isNullOrEmpty()) {
            fetchData()
            return
        }

        tags?.let {
            if ("[A-Za-z,]+".toRegex().matches(tags)) {
                watch(flickrApi.search(tags))
            } else {
                view.onError("Only A-Z a-z and , are accepted as search input")
            }
        }
    }

    private fun watch(source: Single<List<FlickrItem>>) {
        source
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { data ->
                            view.hideLoading()
                            view.onData(dataSorter.byDateAscending(data))
                        },
                        { error ->
                            view.hideLoading()
                            view.onError(error.localizedMessage)
                        }
                )
    }

}
