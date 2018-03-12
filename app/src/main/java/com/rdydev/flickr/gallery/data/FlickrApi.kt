package com.rdydev.flickr.gallery.data

import com.rdydev.flickr.gallery.data.model.FlickrFeed
import com.rdydev.flickr.gallery.data.model.FlickrItem
import io.reactivex.Single
import retrofit2.Retrofit

class FlickrApi {

    val flickrContract: FlickrContract

    constructor(retrofit: Retrofit) {
        flickrContract = retrofit.create(FlickrContract::class.java)
    }

    fun getFeed(): Single<List<FlickrItem>> {
        return flickrContract.fetchFlickrFeed()
                .map { t: FlickrFeed -> t.items }
    }
}