package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class GetAllListsUseCaseImplTest {

    private val listRepository: ListRepository = Mockito.mock()
    private val getAllListsUseCase: GetAllListsUseCase by lazy {
        GetAllListsUseCaseImpl(
            listRepository
        )
    }

    @Test
    fun testGetAllListsUseCase(): Unit = runBlocking {
        /* val items = un objeto tipo LiveData<List<ListCollectionItemModel>>
         whenever(getAllListsUseCase())
             .thenReturn(items)

         val result = getAllListsUseCase()
         assertNotNull(result)
         assertEquals(items, result.value)*/
    }
}