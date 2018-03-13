package com.rdydev.flickr.gallery

import assertk.assert
import assertk.assertions.contains
import com.nhaarman.mockito_kotlin.*
import com.rdydev.flickr.gallery.data.FlickrApi
import com.rdydev.flickr.gallery.data.model.FlickrItem
import com.rdydev.flickr.gallery.rules.RxSchedulersRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FlickrPresenterTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulersRule()

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
        whenever(flickrApi.getFeed()).thenReturn(Single.error(Throwable("oh no")))

        sut.fetchData()

        verify(view).onError(eq("oh no"))
    }

    @Test
    fun dataRequestTogglesLoadingSpinner() {
        val data = mutableListOf(mock<FlickrItem> {})
        whenever(flickrApi.getFeed()).thenReturn(Single.just(data))
        val inOrder = inOrder(view)

        sut.fetchData()

        inOrder.verify(view, times(1)).showLoading()
        inOrder.verify(view, times(1)).hideLoading()
        inOrder.verify(view, times(1)).onData(any())
    }

    @Test
    fun fetchDataErrorTogglesLoadingSpinner() {
        whenever(flickrApi.getFeed()).thenReturn(Single.error(Throwable("oh no")))
        val inOrder = inOrder(view)

        sut.fetchData()

        inOrder.verify(view, times(1)).showLoading()
        inOrder.verify(view, times(1)).hideLoading()
        inOrder.verify(view, times(1)).onError(any())
    }

    @Test
    fun searchShouldFetchNormalDataIfEmpty() {
        val data = mutableListOf(mock<FlickrItem> {})
        whenever(flickrApi.getFeed()).thenReturn(Single.just(data))
        val spy = spy(sut)

        spy.searchTags("")

        verify(spy).fetchData()
    }

    @Test
    fun searchShouldAllowValidInput() {
        val data = mutableListOf(mock<FlickrItem> {})
        whenever(flickrApi.search(any())).thenReturn(Single.just(data))

        sut.searchTags("android,ios")

        verify(view).onData(any())
    }

    @Test
    fun searchShouldNotAllowBadCharacters() {
        val data = mutableListOf(mock<FlickrItem> {})
        whenever(flickrApi.search(any())).thenReturn(Single.just(data))

        sut.searchTags("@£$%^&*")

        argumentCaptor<String>().apply {
            verify(view).onError(capture())
            assert(firstValue).contains("are accepted")
        }
    }
}