package com.rdydev.flickr.gallery.data

import com.rdydev.flickr.gallery.data.model.FlickrFeed
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrContract {

    companion object {
        const val FEED_URL = "/services/feeds/photos_public.gne?format=json&nojsoncallback=true"
    }

    @GET(FEED_URL)
    fun fetchFlickrFeed(): Single<FlickrFeed>

    @GET(FEED_URL)
    fun searchFlickrFeed(@Query("tags") tags: String): Single<FlickrFeed>
}