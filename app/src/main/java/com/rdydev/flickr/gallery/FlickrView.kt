package com.rdydev.flickr.gallery

import com.rdydev.flickr.gallery.data.model.FlickrItem

interface FlickrView {

    fun onError(message: String)

    fun onData(data: List<FlickrItem>)

    fun showLoading()

    fun hideLoading()

}