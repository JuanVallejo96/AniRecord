package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class CreateListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val createListUseCase: CreateListUseCase by lazy { CreateListUseCaseImpl(listRepository) }

    @Test
    fun testGetAllListsUseCase(): Unit = runBlocking {
        whenever(createListUseCase("test"))
            .thenReturn(Unit)

        val result = createListUseCase("test")
        assertEquals(Unit, result)
    }
}