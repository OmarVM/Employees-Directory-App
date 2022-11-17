package com.example.employeesdirectoryapp

import com.example.employeesdirectoryapp.data.api.EmployeesAPI
import com.example.employeesdirectoryapp.data.entity.server.EmployeeRemote
import com.example.employeesdirectoryapp.data.entity.server.ListEmployeesRemote
import com.example.employeesdirectoryapp.data.mapper.EmployeeMapper
import com.example.employeesdirectoryapp.data.mapper.ResponseHandler
import com.example.employeesdirectoryapp.data.repository.NetworkEmployeesRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class NetworkEmployeesRepositoryTest {

    @ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var sut: NetworkEmployeesRepository
    private val mapper = EmployeeMapper()
    private val fake = APIResponseFake()

    @Mock
    private lateinit var api: EmployeesAPI

    @ExperimentalCoroutinesApi
    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        sut = NetworkEmployeesRepository(api,mapper)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get Employee list with at least 2 items return success`() = runBlockingTest {
        `when`(api.getEmployeesList())
           .thenReturn(fake.successResponse())
        val response = sut.getListEmployeesList()
        verify(api).getEmployeesList()
        assert(response is ResponseHandler.SUCCESS)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get Employee list empty return error`() = runBlockingTest {
        `when`(api.getEmployeesList())
            .thenReturn(fake.successResponseEmpty())
        val response = sut.getListEmployeesList()
        verify(api).getEmployeesList()
        assert(response is ResponseHandler.ERROR)
    }
}

class APIResponseFake(){

    fun successResponse(): Response<ListEmployeesRemote> {
       return Response.success(200, getEmployeeResponse())
    }

    private fun getListItem(): List<EmployeeRemote> {
        return listOf(
            EmployeeRemote(
                "0d8fcc12-4d0c-425c-8355-390b312b909c",
                "Justine Mason",
                "5553280123",
                "jmason.demo@squareup.com",
                "Engineer on the Point of Sale team.",
                "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
                "Point of Sale"),
            EmployeeRemote(
                "a98f8a2e-c975-4ba3-8b35-01f719e7de2d",
                "Camille Rogers",
                "5558531970",
                "crogers.demo@squareup.com",
                "Designer on the web marketing team.",
                "https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/small.jpg",
                "Public Web & Marketing"
            )
        )
    }

    fun successResponseEmpty(): Response<ListEmployeesRemote> {
        return Response.success(200, ListEmployeesRemote(emptyList()))
    }

    private fun getEmployeeResponse() : ListEmployeesRemote {
        return ListEmployeesRemote(getListItem())
    }

    private fun getJsonResponse(): String {
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<ListEmployeesRemote> =
            moshi.adapter(ListEmployeesRemote::class.java)
        return jsonAdapter.toJson(getEmployeeResponse())
    }
}