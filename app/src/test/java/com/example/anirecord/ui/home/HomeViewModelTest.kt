package com.example.anirecord.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.anirecord.MainCoroutineRule
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val popularUseCase: GetPopularUseCase = mock()

    private val items = listOf(
        ShowListItemModel(1, "show1", null),
        ShowListItemModel(2, "show2", null),
    )

    @Test
    fun testFirstLoad() = runTest {
        whenever(popularUseCase(anyInt(), any(), anyInt()))
            .doReturn(Pair(items, true))
        val vm = HomeViewModel(popularUseCase)
        val shows = vm.shows.getOrAwaitValue()
        assertEquals(items, shows)
    }

    @Test
    fun testMultipleLoad() = runTest {
        whenever(popularUseCase(anyInt(), any(), anyInt()))
            .doReturn(Pair(items, true))
        val vm = HomeViewModel(popularUseCase)
        var shows = vm.shows.getOrAwaitValue()
        assertEquals(items, shows)
        vm.load()
        shows = vm.shows.getOrAwaitValue()
        assertEquals(items.size * 2, shows.size)
    }

    @Test
    fun testNoMoreData() = runTest {
        whenever(popularUseCase(anyInt(), any(), eq(1)))
            .doReturn(Pair(items, false))
        val vm = HomeViewModel(popularUseCase)
        var shows = vm.shows.getOrAwaitValue()
        assertEquals(items, shows)
        vm.load()
        shows = vm.shows.getOrAwaitValue()
        assertEquals(items, shows)
    }
}