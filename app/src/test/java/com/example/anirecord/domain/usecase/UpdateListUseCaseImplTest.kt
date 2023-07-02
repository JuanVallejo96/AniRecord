package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class UpdateListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val updateListUseCase: UpdateListUseCase by lazy { UpdateListUseCaseImpl(listRepository) }

    @Test
    fun testGetShowsOnListUseCase(): Unit = runBlocking {
        whenever(updateListUseCase(ListCollectionItemModel(1, "test")))
            .thenReturn(Unit)

        val result = updateListUseCase(ListCollectionItemModel(1, "test"))
        Assert.assertEquals(Unit, result)
    }
}