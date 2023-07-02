package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AddToListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val addToListUseCase: AddToListUseCase by lazy { AddToListUseCaseImpl(listRepository) }

    @Test
    fun testGetAllListsUseCase(): Unit = runTest {
        val listId = 1
        val showId = 1
        whenever(listRepository.insertShowIntoList(anyInt(), anyInt()))
            .thenReturn(Unit)

        addToListUseCase(listId, showId)
        verify(listRepository, times(1)).insertShowIntoList(listId, showId)
    }
}