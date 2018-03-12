package com.rdydev.flickr.gallery.data.model

data class FlickrFeed(
        val title: String,
        val link: String,
        var description: String?,
        var modified: String?,
        var generator: String?,
        val items: List<FlickrItem>
)