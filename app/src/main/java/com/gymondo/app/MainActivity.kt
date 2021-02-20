package com.gymondo.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val gitHubSearchUseCase: GitHubSearchUseCase by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runBlocking {
            gitHubSearchUseCase.execute(GitHubSearchUseCase.Params.create("database first", 1, 1))
                .collect {
                    Log.d("FLOWW", it.totalCount.toString())
                }
        }
    }
}