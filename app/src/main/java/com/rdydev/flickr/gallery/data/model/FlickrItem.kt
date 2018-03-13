package com.rdydev.flickr.gallery.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class FlickrItem(
        val title: String,
        val link: String,
        val media: FlickrMedia,
        @SerializedName("date_taken") val date: Date
)