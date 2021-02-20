package com.gymondo.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gymondo.data.repository.GitHubRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val dataSourse: GitHubRemoteDataSource by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runBlocking {
            dataSourse.getRepoDetails("entityframeworktutorial", "EF6-DBFirst-Demo")

                .flowOn(Dispatchers.IO).onStart {
                    // start
                }.catch {
                    // error

                }.collect {
                    Log.d("FLOWW", it.name)
                }

        }
    }
}