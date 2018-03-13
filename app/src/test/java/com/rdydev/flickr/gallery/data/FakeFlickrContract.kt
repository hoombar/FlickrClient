package com.rdydev.flickr.gallery.data

import com.google.gson.Gson
import com.rdydev.flickr.gallery.data.model.FlickrFeed
import io.reactivex.Single
import junit.framework.Assert
import java.io.IOException

class FakeFlickrContract(caller: Any) : FlickrContract {
    val classLoader: ClassLoader

    init {
        classLoader = caller.javaClass.classLoader
    }

    lateinit var sourceFile: String

    override fun fetchFlickrFeed(): Single<FlickrFeed> {
        val flickrFeed = Gson().fromJson(sourceFile.readAsFile(), FlickrFeed::class.java)

        return Single.just(flickrFeed)
    }

    override fun searchFlickrFeed(tags: String): Single<FlickrFeed> {
        return fetchFlickrFeed()
    }

    fun String.readAsFile(): String {
        try {
            val stream = classLoader.getResourceAsStream(this)
            return stream.bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Assert.fail("Could not open file")
            throw RuntimeException("Could not open file")
        }
    }

}