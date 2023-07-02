package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.domain.repository.StaffRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class GetVoiceActorsListUseCaseImplTest {
    private val staffRepository: StaffRepository = mock()
    private val getVoiceActorListUseCase: GetVoiceActorsListUseCase by lazy {
        GetVoiceActorsListUseCaseImpl(
            staffRepository
        )
    }

    @Test
    fun testGetVoiceActorListUseCase() = runBlocking {
        val moreItems = false
        val items = listOf(
            ShowVoiceActorModel(
                0,
                "actorName",
                "actorImage",
                "characterName",
                "characterImage",
                "role"
            )
        )
        whenever(staffRepository.getShowVoiceActors(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(Pair(items, moreItems))

        val result = getVoiceActorListUseCase(0, 0)
        Assert.assertNotNull(result)
        Assert.assertEquals(moreItems, result!!.second)
        Assert.assertEquals(items, result.first)
    }
}