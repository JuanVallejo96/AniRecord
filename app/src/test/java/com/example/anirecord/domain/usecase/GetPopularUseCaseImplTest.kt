package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.graphql.type.MediaSeason
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class GetPopularUseCaseImplTest {
    private val showRepository: ShowRepository = mock()
    private val getPopularUseCase: GetPopularUseCase by lazy { GetPopularUseCaseImpl(showRepository) }

    @Test
    fun testGetPopularUseCase(): Unit = runBlocking {
        val moreItems = false
        val items = listOf(ShowListItemModel(0, "Test", ""))
        whenever(showRepository.getPopularOnSeason(anyInt(), any(), anyInt()))
            .thenReturn(Pair(items, moreItems))

        val result = getPopularUseCase(0, MediaSeason.SUMMER, 0)
        assertNotNull(result)
        assertEquals(moreItems, result!!.second)
        assertEquals(items, result.first)
    }
}