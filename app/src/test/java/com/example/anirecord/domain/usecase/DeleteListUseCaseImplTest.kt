package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class DeleteListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val deleteListUseCase: DeleteListUseCase by lazy { DeleteListUseCaseImpl(listRepository) }

    @Test
    fun testGetAllListsUseCase(): Unit = runBlocking {
        whenever(deleteListUseCase(1))
            .thenReturn(Unit)

        val result = deleteListUseCase(1)
        assertEquals(Unit, result)
    }
}