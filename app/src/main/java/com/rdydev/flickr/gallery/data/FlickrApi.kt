package com.rdydev.flickr.gallery.data

import com.rdydev.flickr.gallery.data.model.FlickrFeed
import com.rdydev.flickr.gallery.data.model.FlickrItem
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit

class FlickrApi {

    val flickrContract: FlickrContract

    constructor(contract: FlickrContract) {
        this.flickrContract = contract
    }

    fun getFeed() : Single<List<FlickrItem>> {
        return getInnerItems(flickrContract.fetchFlickrFeed())
    }

    fun search(tags: String) : Single<List<FlickrItem>> {
        return getInnerItems(flickrContract.searchFlickrFeed(tags))
    }

    private fun getInnerItems(feed: Single<FlickrFeed>) : Single<List<FlickrItem>> {
        return feed.map { t: FlickrFeed -> t.items }
    }
}