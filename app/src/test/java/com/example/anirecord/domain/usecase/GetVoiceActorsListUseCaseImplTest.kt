package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.domain.repository.StaffRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

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
        TestCase.assertNotNull(result)
        TestCase.assertEquals(moreItems, result!!.second)
        TestCase.assertEquals(items, result.first)
    }
}