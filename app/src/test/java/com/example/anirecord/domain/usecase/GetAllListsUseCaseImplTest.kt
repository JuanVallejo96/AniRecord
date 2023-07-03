package com.example.anirecord.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.example.anirecord.MainCoroutineRule
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.repository.ListRepository
import com.example.anirecord.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetAllListsUseCaseImplTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val listRepository: ListRepository = Mockito.mock()
    private val getAllListsUseCase: GetAllListsUseCase by lazy {
        GetAllListsUseCaseImpl(
            listRepository
        )
    }

    private val items = listOf(
        ListCollectionItemModel(1, "list1"),
        ListCollectionItemModel(2, "list2"),
    )

    @Test
    fun testGetAllListsUseCase(): Unit = runTest {
        whenever(listRepository.getAll())
            .doReturn(liveData { emit(items) })

        val result = getAllListsUseCase().getOrAwaitValue()
        Assert.assertEquals(items, result)
    }
}