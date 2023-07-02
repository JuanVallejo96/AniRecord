package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class DeleteShowFromListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val deleteShowFromListUseCase: DeleteShowFromListUseCase by lazy {
        DeleteShowFromListUseCaseImpl(
            listRepository
        )
    }

    @Test
    fun testGetAllListsUseCase(): Unit = runBlocking {
        whenever(deleteShowFromListUseCase(1, 1))
            .thenReturn(Unit)

        val result = deleteShowFromListUseCase(1, 1)
        assertEquals(Unit, result)
    }
}