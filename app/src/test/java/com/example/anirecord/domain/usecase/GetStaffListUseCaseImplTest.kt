package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.repository.StaffRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

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
        val items = listOf(ShowStaffListItemModel("name", "image", "role"))
        whenever(staffRepository.getShowStaff(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(Pair(items, moreItems))

        val result = getStaffListUseCase(0, 0)
        TestCase.assertNotNull(result)
        TestCase.assertEquals(moreItems, result!!.second)
        TestCase.assertEquals(items, result.first)
    }
}