package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class DeleteListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val deleteListUseCase: DeleteListUseCase by lazy { DeleteListUseCaseImpl(listRepository) }

    @Test
    fun testGetAllListsUseCase(): Unit = runTest {
        val id = 1
        whenever(listRepository.delete(anyInt()))
            .thenReturn(Unit)

        deleteListUseCase(id)
        verify(listRepository, times(1)).delete(id)
    }
}