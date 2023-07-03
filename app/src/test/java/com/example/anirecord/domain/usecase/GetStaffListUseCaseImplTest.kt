package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.repository.StaffRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class GetStaffListUseCaseImplTest {
    private val staffRepository: StaffRepository = mock()
    private val getStaffListUseCase: GetStaffListUseCase by lazy {
        GetStaffListUseCaseImpl(
            staffRepository
        )
    }

    @Test
    fun testGetStaffListUseCase(): Unit = runBlocking {
        val moreItems = false
        val items = listOf(ShowStaffListItemModel(0, "name", "image", "role"))
        whenever(staffRepository.getShowStaff(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(Pair(items, moreItems))

        val result = getStaffListUseCase(0, 0)
        Assert.assertNotNull(result)
        Assert.assertEquals(moreItems, result!!.second)
        Assert.assertEquals(items, result.first)
    }
}