package com.gymondo.app.remote

import com.gymondo.app.remote.di.remoteModule
import com.gymondo.data.repository.GitHubRemoteDataSource
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import java.io.File

class GitHubRemoteDataSourceImplTest : AutoCloseKoinTest() {

    private lateinit var mockServer: MockWebServer
    private lateinit var gitHubRemoteDataSource: GitHubRemoteDataSource

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        startKoin {
            modules(listOf(remoteModule))
        }
        gitHubRemoteDataSource = get()
    }

    @Test
    fun `test github search with success response`() = runBlocking {

        val mockedResponse = MockResponse().apply {
            setResponseCode(200)
            setBody(getJson("json/GitHub/repo_search.json"))
        }
        mockServer.enqueue(mockedResponse)
        val result = gitHubRemoteDataSource.searchRepositories("any", 1, 1).first()
        assert(result != null)
    }


    @Test(expected = Exception::class)
    fun `test github search with error response`() = runBlocking {

        val mockedResponse = MockResponse().apply {
            setResponseCode(404)
            setBody(getJson("json/GitHub/error.json"))
        }
        mockServer.enqueue(mockedResponse)
        gitHubRemoteDataSource.searchRepositories(any(), any(), any()).first()

    }


    @Test
    fun `test get repository details with success response`() = runBlocking {

        val mockedResponse = MockResponse().apply {
            setResponseCode(200)
            setBody(getJson("json/GitHub/repo_details.json"))
        }
        mockServer.enqueue(mockedResponse)
        val result =
            gitHubRemoteDataSource.getRepoDetails("entityframeworktutorial", "EF6-DBFirst-Demo")
//        assert(result is DataResult.Success)
    }


    @Test
    fun `test get repository details with error response`() = runBlocking {

        val mockedResponse = MockResponse().apply {
            setResponseCode(404)
            setBody(getJson("json/GitHub/error.json"))
        }
        mockServer.enqueue(mockedResponse)
        val result = gitHubRemoteDataSource.getRepoDetails("user", "repoName")
//        assert(result is DataResult.Error)
    }

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}