package com.rdydev.flickr.gallery

import com.rdydev.flickr.gallery.data.DataModule
import com.rdydev.flickr.gallery.data.FlickrApi
import com.rdydev.flickr.gallery.data.model.FlickrItem
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

internal class FlickrPresenter {

    private var view: FlickrView
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
                .subscribe(object : SingleObserver<List<FlickrItem>> {
                    override fun onSuccess(t: List<FlickrItem>) {
                        view.hideLoading()
                        view.onData(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        // NO_OP
                    }

                    override fun onError(e: Throwable) {
                        view.hideLoading()
                        view.onError(e?.localizedMessage)
                    }

                })
    }

}
