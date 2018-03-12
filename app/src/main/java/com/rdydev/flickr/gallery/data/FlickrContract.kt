package com.rdydev.flickr.gallery.data

import com.rdydev.flickr.gallery.data.model.FlickrFeed
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface FlickrContract {

    companion object {
        const val FEED_URL = "/services/feeds/photos_public.gne?format=json&nojsoncallback=?"
    }

    @GET(FEED_URL)
    fun fetchFlickrFeed() : Single<FlickrFeed>

}