package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.graphql.type.MediaSeason
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class GetPopularUseCaseImplTest {
    private val showRepository: ShowRepository = Mockito.mock()
    private val getPopularUseCase: GetPopularUseCase by lazy { GetPopularUseCaseImpl(showRepository) }

    @Test
    fun testGetPopularUseCase(): Unit = runBlocking {
        val moreItems = false
        val items = listOf(ShowListItemModel(0, "Test", ""))
        whenever(showRepository.getPopularOnSeason(anyInt(), any(), anyInt()))
            .thenReturn(Pair(items, moreItems))

        val result = getPopularUseCase(0, MediaSeason.SUMMER, 0)
        Assert.assertNotNull(result)
        Assert.assertEquals(moreItems, result!!.second)
        Assert.assertEquals(items, result.first)
    }
}