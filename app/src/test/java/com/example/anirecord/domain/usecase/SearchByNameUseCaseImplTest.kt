package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
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
        whenever(showRepository.searchByQuery(anyString(), Mockito.anyInt()))
            .thenReturn(Pair(items, moreItems))

        val result = searchByNameUseCase("Test", 0)
        Assert.assertNotNull(result)
        Assert.assertEquals(moreItems, result!!.second)
        Assert.assertEquals(items, result.first)
    }
}