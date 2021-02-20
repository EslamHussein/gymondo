package com.gymondo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gymondo.presentaion.GitHubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: GitHubViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.search("data")
    }
}