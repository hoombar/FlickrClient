package com.rdydev.flickr.gallery.data.model

// here be dragons. I'm not familiar with the Flickr API, and unsure whether this field may not come back, the
// assumption I have is that it is providing a size and they could easily choose to return one of the other sizes
// which would make the parsing blow up.

// Given more time, I would parse other image sizes and have a check for nullity to determine which to use

data class FlickrMedia(
        val m: String
)