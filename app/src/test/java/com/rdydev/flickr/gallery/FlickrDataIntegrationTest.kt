package com.rdydev.flickr.gallery

import assertk.assert
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.rdydev.flickr.gallery.data.FakeFlickrContract
import com.rdydev.flickr.gallery.data.FlickrApi
import com.rdydev.flickr.gallery.data.model.FlickrItem
import com.rdydev.flickr.gallery.rules.RxSchedulersRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FlickrDataIntegrationTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulersRule()

    @Mock
    private lateinit var view: FlickrView

    private lateinit var fakeFeed: FakeFlickrContract

    private val sut: FlickrPresenter by lazy {
        FlickrPresenter(view, FlickrApi(fakeFeed))
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        fakeFeed = FakeFlickrContract(this)
    }

    @Test
    fun parseFeedJsonWithGoodDataSuccess() {
        fakeFeed.sourceFile = "flickr_feed_success.json"

        sut.fetchData()

        argumentCaptor<List<FlickrItem>>().apply {
            verify(view).onData(capture())

            assert(firstValue.get(0).title).isEqualTo("2018-03-12_01-01-07")
        }
    }

    @Test
    fun parseFeedJsonWithBadDataFailure() {
        fakeFeed.sourceFile = "flickr_feed_bad_data.json"

        sut.fetchData()

        verify(view).onError(anyString())
    }

}