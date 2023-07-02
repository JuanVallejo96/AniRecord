package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class GetShowsOnListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val getShowsOnListUseCase: GetShowsOnListUseCase by lazy {
        GetShowsOnListUseCaseImpl(
            listRepository
        )
    }

    @Test
    fun testGetShowsOnListUseCase(): Unit = runBlocking {
        /*val items = un objeto de tipo LiveData<List<ShowListItemModel>>
        whenever(getShowsOnListUseCase(1))
            .thenReturn(items)

        val result = getShowsOnListUseCase(1)
        assertNotNull(result)
        assertEquals(items, result.value)*/
    }
}