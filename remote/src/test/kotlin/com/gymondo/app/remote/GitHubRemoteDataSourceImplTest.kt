package com.gymondo.app.remote

import app.cash.turbine.test
import com.gymondo.app.remote.di.remoteModule
import com.gymondo.data.repository.GitHubRemoteDataSource
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import java.io.File
import kotlin.time.ExperimentalTime

class GitHubRemoteDataSourceImplTest : AutoCloseKoinTest() {

    private lateinit var mockServer: MockWebServer
    private lateinit var gitHubRemoteDataSource: GitHubRemoteDataSource

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        startKoin {
            modules(listOf(remoteModule))
        }
        gitHubRemoteDataSource = get()
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun `test github search with success response`() = dispatcher.runBlockingTest {

        val mockedResponse = MockResponse().apply {
            setResponseCode(200)
            setBody(getJson("json/GitHub/repo_search.json"))
        }
        mockServer.enqueue(mockedResponse)
        gitHubRemoteDataSource.searchRepositories("any", 1, 1)
            .test {
                expectItem()
                expectComplete()
            }

    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test(expected = Exception::class)
    fun `test github search with error response`() = dispatcher.runBlockingTest {

        val mockedResponse = MockResponse().apply {
            setResponseCode(404)
            setBody(getJson("json/GitHub/error.json"))
        }
        mockServer.enqueue(mockedResponse)
        gitHubRemoteDataSource.searchRepositories(any(), any(), any()).test {
            expectError()
        }
    }


    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun `test get repository details with success response`() = dispatcher.runBlockingTest {

        val mockedResponse = MockResponse().apply {
            setResponseCode(200)
            setBody(getJson("json/GitHub/repo_details.json"))
        }
        mockServer.enqueue(mockedResponse)

        gitHubRemoteDataSource.getRepoDetails("entityframeworktutorial", "EF6-DBFirst-Demo").test {
            expectItem()
            expectComplete()
        }

    }


    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun `test get repository details with error response`() = dispatcher.runBlockingTest {

        val mockedResponse = MockResponse().apply {
            setResponseCode(404)
            setBody(getJson("json/GitHub/error.json"))
        }
        mockServer.enqueue(mockedResponse)
        gitHubRemoteDataSource.getRepoDetails("user", "repoName").test {
            expectError()
        }

    }

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}