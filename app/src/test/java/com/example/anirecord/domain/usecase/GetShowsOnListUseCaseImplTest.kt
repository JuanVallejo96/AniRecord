package com.example.anirecord.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.example.anirecord.MainCoroutineRule
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ListRepository
import com.example.anirecord.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetShowsOnListUseCaseImplTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val listRepository: ListRepository = Mockito.mock()
    private val getShowsOnListUseCase: GetShowsOnListUseCase by lazy {
        GetShowsOnListUseCaseImpl(
            listRepository
        )
    }

    private val items = listOf(
        ShowListItemModel(0, "show1", "cover"),
        ShowListItemModel(1, "show2", "cover"),
    )

    @Test
    fun testGetShowsOnListUseCase(): Unit = runTest {
        whenever(listRepository.findShowsById(anyInt()))
            .doReturn(liveData { emit(items) })

        val result = getShowsOnListUseCase(1).getOrAwaitValue()
        Assert.assertEquals(items, result)
    }
}