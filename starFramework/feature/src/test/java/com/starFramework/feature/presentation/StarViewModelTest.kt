package com.starFramework.feature.presentation

import com.starFramework.feature.domain.model.Star
import com.starFramework.feature.domain.usecase.StarUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StarViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var starUseCase: StarUseCase

    private lateinit var viewModel: StarViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = StarViewModel(starUseCase)
    }

    @Test
    fun `addStar increases star count correctly`() = runTest {
        val initialStar = Star(size = "S", color = "Blue", brightness = "Bright")
        viewModel.addStar(initialStar)

        val starCount = viewModel.stars.value.size
        assertEquals(1, starCount)
    }

    @Test
    fun `resetSky clears the star list`() = runTest {
        viewModel.addStar(Star(size = "S", color = "Blue", brightness = "Bright"))
        viewModel.addStar(Star(size = "B", color = "Red", brightness = "Not so much"))

        viewModel.resetSky()

        val starCount = viewModel.stars.value.size
        assertEquals(0, starCount)
    }

    @Test
    fun `isSkyFull returns true when 10 stars are added`() = runTest {
        repeat(11) {
            viewModel.addStar(Star(size = "S", color = "Blue", brightness = "Bright"))
        }

        advanceUntilIdle()

        assertTrue(viewModel.isSkyFull.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}