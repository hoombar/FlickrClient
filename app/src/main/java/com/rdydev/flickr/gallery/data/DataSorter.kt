package com.rdydev.flickr.gallery.data

import com.rdydev.flickr.gallery.data.model.FlickrItem

class DataSorter {

    fun byDateAscending(items: List<FlickrItem>) : List<FlickrItem> {
        return items.sortedWith(compareBy(FlickrItem::date))
    }

    fun byDateDescending(items: List<FlickrItem>) : List<FlickrItem> {
        return items.sortedWith(compareByDescending(FlickrItem::date))
    }

}