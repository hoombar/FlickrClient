package com.rdydev.flickr.gallery

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.rdydev.flickr.gallery.data.FlickrApi
import com.rdydev.flickr.gallery.data.model.FlickrItem
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FlickrPresenterTest {

    @Rule @JvmField var testSchedulerRule = ImmediateSchedulersRule()

    @Mock
    private lateinit var flickrApi: FlickrApi
    @Mock
    private lateinit var view: FlickrView

    private val sut: FlickrPresenter by lazy {
        FlickrPresenter(view, flickrApi)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetchDataSuccessInformsView() {
        val data = mutableListOf(mock<FlickrItem> {})
        whenever(flickrApi.getFeed()).thenReturn(Single.just(data))

        sut.fetchData()

        verify(view).onData(eq(data))
    }

    @Test
    fun fetchDataErrorInformsView() {
        val data = mutableListOf(mock<FlickrItem> {})
        whenever(flickrApi.getFeed()).thenReturn(Single.error(Throwable("oh no")))

        sut.fetchData()

        verify(view).onError(eq("oh no"))
    }

}