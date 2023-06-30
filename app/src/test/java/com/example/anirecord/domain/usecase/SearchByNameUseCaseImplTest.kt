package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchByNameUseCaseImplTest {
    private val showRepository: ShowRepository = mock()
    private val searchByNameUseCase: SearchByNameUseCase by lazy {
        SearchByNameUseCaseImpl(
            showRepository
        )
    }

    @Test
    fun testSearchByNameUseCase() = runBlocking {
        val moreItems = false
        val items = listOf(ShowListItemModel(0, "Test", ""))
        whenever(showRepository.getSearch(anyString(), Mockito.anyInt()))
            .thenReturn(Pair(items, moreItems))

        val result = searchByNameUseCase("Test", 0)
        TestCase.assertNotNull(result)
        TestCase.assertEquals(moreItems, result!!.second)
        TestCase.assertEquals(items, result.first)
    }
}