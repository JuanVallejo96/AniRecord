package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class UpdateListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val updateListUseCase: UpdateListUseCase by lazy { UpdateListUseCaseImpl(listRepository) }

    @Test
    fun testGetShowsOnListUseCase(): Unit = runBlocking {
        whenever(updateListUseCase(ListCollectionItemModel(1, "test")))
            .thenReturn(Unit)

        val result = updateListUseCase(ListCollectionItemModel(1, "test"))
        assertEquals(Unit, result)
    }
}