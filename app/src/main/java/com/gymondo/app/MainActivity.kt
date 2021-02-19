package com.gymondo.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gymondo.app.remote.GitHubRemoteDataSource
import com.gymondo.app.remote.core.dto.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val dataSourse: GitHubRemoteDataSource by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runBlocking {
            flow {
                val searchRepositories =
                    dataSourse.getRepoDetails("entityframeworktutorial", "EF6-DBFirst-Demo")
                emit(searchRepositories)

            }.flowOn(Dispatchers.IO).onStart {
                // start
            }.catch {
                // error

            }.collect {
                when (it) {
                    is ApiResult.Error -> {
                        Log.d("FLOWW", it.exception.localizedMessage)
                    }
                    is ApiResult.Success -> Log.d("FLOWW", it.data.name)
                }
            }

        }
    }
}