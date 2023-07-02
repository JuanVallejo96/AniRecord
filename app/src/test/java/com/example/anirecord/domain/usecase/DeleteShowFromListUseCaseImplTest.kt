package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class DeleteShowFromListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val deleteShowFromListUseCase: DeleteShowFromListUseCase by lazy {
        DeleteShowFromListUseCaseImpl(
            listRepository
        )
    }

    @Test
    fun testGetAllListsUseCase(): Unit = runTest {
        val listId = 1
        val showId = 1
        whenever(listRepository.deleteShowFromList(anyInt(), anyInt()))
            .thenReturn(Unit)

        deleteShowFromListUseCase(listId, showId)
        verify(listRepository, times(1)).deleteShowFromList(listId, showId)
    }
}