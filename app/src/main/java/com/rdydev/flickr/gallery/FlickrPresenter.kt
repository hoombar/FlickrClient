package com.rdydev.flickr.gallery

import com.rdydev.flickr.gallery.data.DataModule
import com.rdydev.flickr.gallery.data.FlickrApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class FlickrPresenter {

    private lateinit var view: FlickrView
    private val flickrApi: FlickrApi

    constructor(view: FlickrView) : this(view, DataModule.provideFlickrApi())

    constructor(view: FlickrView, flickrApi: FlickrApi) {
        this.view = view
        this.flickrApi = flickrApi
    }

    fun fetchData() {
        view.showLoading()

        flickrApi.getFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { data ->
                            view.hideLoading()
                            view.onData(data)
                        },
                        { error ->
                            view.hideLoading()
                            view.onError(error.localizedMessage)
                        }
                )
    }

    fun searchTags(tags: String?) {
        if (tags.isNullOrEmpty()) {
            fetchData()
            return
        }

        tags?.let {
            if ("[A-Za-z,]+".toRegex().matches(tags)) {
                flickrApi.search(tags)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { data ->
                                    view.hideLoading()
                                    view.onData(data)
                                },
                                { error ->
                                    view.hideLoading()
                                    view.onError(error.localizedMessage)
                                }
                        )
            } else {
                view.onError("Only A-Z a-z and , are accepted as search input")
            }
        }
    }

}
