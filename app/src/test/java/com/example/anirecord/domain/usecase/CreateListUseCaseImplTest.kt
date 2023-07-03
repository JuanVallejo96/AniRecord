package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class CreateListUseCaseImplTest {
    private val listRepository: ListRepository = Mockito.mock()
    private val createListUseCase: CreateListUseCase by lazy { CreateListUseCaseImpl(listRepository) }

    @Test
    fun testGetAllListsUseCase(): Unit = runTest {
        val name = "test"
        whenever(listRepository.insert(any()))
            .thenReturn(Unit)

        createListUseCase(name)
        verify(listRepository, times(1)).insert(name)
    }
}