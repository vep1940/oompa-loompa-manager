package com.example.oompaloompamanager

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.data.datasources.DataSource
import com.example.oompaloompamanager.data.models.AdditionalInfoResponse
import com.example.oompaloompamanager.data.models.OompaLoompaDetailResponse
import com.example.oompaloompamanager.data.repositories.ImplWorkerRepository
import com.example.oompaloompamanager.domain.constants.Gender
import com.example.oompaloompamanager.domain.models.AdditionalInfo
import com.example.oompaloompamanager.domain.models.OompaLoompaDetail
import com.example.oompaloompamanager.domain.repositories.WorkerRepository
import io.mockk.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dataTest() = runBlocking {

        val expected = OompaLoompaDetail(
            "Antonio",
            "Rodríguez",
            Gender.MALE,
            "Image",
            "Metalworker",
            "Email",
            100,
            100,
            "Quota",
            AdditionalInfo(
                "Magenta",
                "Spanish tomato soup"
            )
        )

        val localDatasource = mockk<DataSource.Local>(relaxed = true)
        val networkDatasource = mockk<DataSource.Network>()

        every { localDatasource.getWorkerDetail(any()) } returns AppResponse.ResponseOk(null)

        coEvery { networkDatasource.getWorkerDetail(any()) } returns AppResponse.ResponseOk(
            OompaLoompaDetailResponse(
                "Antonio",
                "Rodríguez",
                "Description",
                "Image",
                "Metalworker",
                "Quota",
                100,
                "Spain",
                100,
                AdditionalInfoResponse(
                    "Magenta",
                    "Spanish tomato soup",
                    "Random string",
                    "Song"
                ),
                "M",
                "Email"
            )
        )

        val workerRepository = ImplWorkerRepository(localDatasource, networkDatasource)

        workerRepository.getWorkerDetail(2).collect { result ->

            coVerify(exactly = 1) { networkDatasource.getWorkerDetail(any()) }

            assertTrue(result is AppResponse.ResponseOk<OompaLoompaDetail>)

            assertEquals(expected, (result as AppResponse.ResponseOk<OompaLoompaDetail>).value)
        }
    }
}