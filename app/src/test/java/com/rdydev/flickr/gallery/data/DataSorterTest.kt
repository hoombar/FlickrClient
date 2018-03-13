package com.rdydev.flickr.gallery.data

import assertk.assert
import assertk.assertions.isEqualTo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.rdydev.flickr.gallery.data.model.FlickrItem
import com.rdydev.flickr.gallery.data.model.FlickrMedia
import org.junit.Test
import java.text.ParsePosition
import java.util.*

class DataSorterTest {

    val sut = DataSorter()

    @Test
    fun sortByDate() {
        val data = listOf(
                FlickrItem("item 1", "link 1", FlickrMedia(m = ""), dateFrom("2018-03-13T16:52:28Z")),
                FlickrItem("item 2", "link 2", FlickrMedia(m = ""), dateFrom("2018-03-13T16:51:28Z")),
                FlickrItem("item 3", "link 3", FlickrMedia(m = ""), dateFrom("2018-03-13T16:53:28Z"))
        )

        val byDate = sut.byDateAscending(data)

        assert(byDate.get(0).title).isEqualTo("item 2")
        assert(byDate.get(1).title).isEqualTo("item 1")
        assert(byDate.get(2).title).isEqualTo("item 3")
    }

    @Test
    fun sortByMostRecentDateFirst() {
        val data = listOf(
                FlickrItem("item 1", "link 1", FlickrMedia(m = ""), dateFrom("2018-03-13T16:52:28Z")),
                FlickrItem("item 2", "link 2", FlickrMedia(m = ""), dateFrom("2018-03-13T16:51:28Z")),
                FlickrItem("item 3", "link 3", FlickrMedia(m = ""), dateFrom("2018-03-13T16:53:28Z"))
        )

        val byDate = sut.byDateDescending(data)

        assert(byDate.get(0).title).isEqualTo("item 3")
        assert(byDate.get(1).title).isEqualTo("item 1")
        assert(byDate.get(2).title).isEqualTo("item 2")
    }

    private fun dateFrom(value: String): Date {
        // here be dragons; I'm using the internal ISO 8601 date parser from Gson. If they update this, this code
        // might break, but in the interest of time, I'm using this to parse the dates and keep the test readable
        return ISO8601Utils.parse(value, ParsePosition(0))
    }

}